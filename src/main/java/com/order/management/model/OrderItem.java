package com.order.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long quantity;

  private String name;
  private Double price;

  public Long getQuantity() {
    return this.quantity;
  }

  public Double getPrice() {
    return this.price;
  }

  public Long getProductId() {
    return this.id;
  }

  public void setStock(Long amount) {
    this.quantity = amount;
  }

  public String getName() {
    return this.name;
  }
}
