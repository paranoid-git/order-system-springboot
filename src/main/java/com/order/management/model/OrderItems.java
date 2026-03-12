package com.order.management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import com.order.management.model.OrderItem;

@Entity
@Table(name = "order_items")
public class OrderItems {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private OrderItem product; // ← reference to product

  private Long orderQuantity; // ← how many customer wants

  public Long getId() {
    return id;
  }

  public OrderItem getProduct() {
    return product;
  }

  public Long getOrderQuantity() {
    return orderQuantity;
  }

  public void setProduct(OrderItem product) {
    this.product = product;
  }

  public void setOrderQuantity(Long orderQuantity) {
    this.orderQuantity = orderQuantity;
  }
}
