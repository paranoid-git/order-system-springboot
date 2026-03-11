package com.order.management.services;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.order.management.dto.PostOrder;
import org.json.simple.JSONObject;
import com.order.management.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

public class OrderService {
  @Autowired
  private ModelMapper modelMapper;

  public Order createOrder(@RequestBody PostOrder requestBody) {
    modelMapper = new ModelMapper();
    Random rand = new Random();
    Long orderId = rand.nextLong(10000);
    Order order = modelMapper.map(requestBody, Order.class);
    order.setOrderId(orderId);
    order.setTotalAmount(50.00);
    order.setDate(LocalDate.now());
    order.setStatus("PENDING");
    order.setMessage("Order Created Successfully!");
    return order;
  }
}
