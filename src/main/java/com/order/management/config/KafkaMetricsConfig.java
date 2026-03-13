package com.order.management.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class KafkaMetricsConfig {

  private final KafkaProperties kafkaProperties;
  private final MeterRegistry meterRegistry;

  public KafkaMetricsConfig(KafkaProperties kafkaProperties, MeterRegistry meterRegistry) {
    this.kafkaProperties = kafkaProperties;
    this.meterRegistry = meterRegistry;
  }

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    var factory = new DefaultKafkaProducerFactory<String, String>(
        kafkaProperties.buildProducerProperties());
    // Binds all producer metrics (record-send-rate, batch-size-avg, etc.)
    factory.addListener(new MicrometerProducerListener<>(meterRegistry));
    return factory;
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    var props = kafkaProperties.buildConsumerProperties();
    var factory = new DefaultKafkaConsumerFactory<String, String>(props);
    // Binds all consumer metrics (fetch-latency, commit-rate, lag, etc.)
    factory.addListener(new MicrometerConsumerListener<>(meterRegistry));
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(consumerFactory());
    // MANUAL_IMMEDIATE gives you finer control over offset commits & lag
    // measurement
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
    return factory;
  }
}
