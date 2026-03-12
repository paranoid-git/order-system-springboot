package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.order.management.dto.Address;
import jakarta.persistence.Embedded;

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

  public Long getUserId() {
    return this.userId;
  }

  public String getPaymentMethod() {
    return this.paymentMethod;
  }

  public Address getShippingAddress() {
    return this.shippingAddress;
  }

  public String getShippingMethod() {
    return this.shippingMethod;
  }

  public List<OrderItemRequest> getItems() {
    return this.items;
  }
}
