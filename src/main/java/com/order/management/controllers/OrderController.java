package com.order.management.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.order.management.dto.PostOrder;
import com.order.management.services.OrderService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.order.management.model.Order;

@RestController
@RequestMapping("/api")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @PostMapping("/orders")
  public ResponseEntity<Order> createOrder(@RequestBody PostOrder orderRequest) {
    Order response = orderService.createOrder(orderRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
