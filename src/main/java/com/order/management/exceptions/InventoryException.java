package com.order.management.exceptions;

import com.order.management.model.Order;

public class InventoryException extends RuntimeException {
  private String message;

  public InventoryException(String message, Order order) {
    super(message);
    this.message = message;
    order.setStatus("CANCELLED");
  }
}
