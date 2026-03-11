package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Long orderId;
  private Long userId;
  private LocalDate date;
  private String status;
  private Double totalAmount;
  private List<OrderItemRequest> items;
  private Address shippingAddress;
  private String paymentMethod;
  private String shippingMethod;
  private String message;
}
