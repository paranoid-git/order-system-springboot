package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOrder {
  private Long userId;
  private Address shippingAddress;
  private Address billingAddress;
  private List<OrderItemRequest> items;
  private String paymentMethod;
  private String shippingMethod;
}
