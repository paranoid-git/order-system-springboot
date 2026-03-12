package com.order.management.exceptions;

import com.order.management.model.Order;

public class OrderValidationException extends RuntimeException {
  private String message;

  public OrderValidationException(String message, Order order) {
    super(message);
    this.message = message;
    order.setStatus("CANCELLED");
    order.setMessage(message);
  }
}
