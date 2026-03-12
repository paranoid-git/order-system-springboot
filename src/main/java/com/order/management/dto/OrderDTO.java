package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.order.management.dto.Address;

import jakarta.persistence.Embedded;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

  private Long userId;
  private LocalDate date;
  private String status;
  private Double totalAmount;
  private List<OrderItemRequest> items;
  private Address shippingAddress;
  private String paymentMethod;
  private String shippingMethod;
  private String message;

  public void setTotalAmount(Double amount) {
    this.totalAmount = amount;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getUserId() {
    return this.userId;
  }

  public List<OrderItemRequest> getItems() {
    return this.items;
  }

}
