package com.order.management.exceptions;

import com.order.management.model.Order;

public class OrderProcessingException extends RuntimeException {
  private String message;

  public OrderProcessingException(String message) {
    super(message);
    this.message = message;
  }

  public OrderProcessingException(String message, Order order) {
    super(message);
    this.message = message;
    order.setStatus("CANCELLED");
  }
}
