package com.order.management.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.order.management.repository.OrderRepository;
import com.order.management.repository.UserRepository;
import com.order.management.dto.OrderDTO;
import com.order.management.exceptions.OrderProcessingException;
import jakarta.transaction.Transactional;
import com.order.management.model.User;
import org.slf4j.Logger;
import com.order.management.model.Order;
import org.springframework.context.annotation.Lazy;

@Service
public class OrderProcessingService {
  private static final Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  private OrderService orderService;

  public OrderProcessingService(@Lazy OrderService orderService) {
    this.orderService = orderService;
  }

  @Autowired
  private InventoryService inventoryService;

  @Autowired
  private EmailService emailService;

  @Async
  @Transactional
  public void processOrderAsync(Long orderId) {
    try {
      Order order = orderRepository.findById(orderId)
          .orElseThrow(() -> new OrderProcessingException("Failed to find order by id."));
      logger.info("Found order: " + orderId);
      User user = userRepository.findById(order.getUserId())
          .orElseThrow(() -> new OrderProcessingException("Failed to find user by id.", order));
      logger.info("Found user: " + order.getUserId());
      logger.info("Validating order: " + orderId);
      orderService.validateOrder(order);

      logger.info("Reserving inventory for order: " + orderId + " | Items: " + order.getItems());
      inventoryService.reserveInventory(order);
      order.setStatus("PROCESSING");
      orderRepository.save(order);

      logger.info("Processing payment for order: " + orderId);
      // CURRENTLY UNIMPLEMENTED
      // paymentService.processPayment(order, paymentToken);

      logger.info("Sending confirmation email for order: " + orderId);
      emailService.sendOrderConfirmation(order, user);
    } catch (Exception e) {
      logger.error(e.toString());
    }
  }
}
