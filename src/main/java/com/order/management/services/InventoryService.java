package com.order.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.management.dto.OrderDTO;
import com.order.management.dto.OrderItemRequest;
import com.order.management.repository.ProductRepository;
import com.order.management.exceptions.InventoryException;
import com.order.management.model.Order;
import com.order.management.model.OrderItem;
import com.order.management.model.OrderItems;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Service
public class InventoryService {
  private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

  @Autowired
  private ProductRepository productRepository;

  public void reserveInventory(Order order) {
    for (OrderItems item : order.getItems()) {
      OrderItem product = item.getProduct();
      logger.info("Order quantity: " + item.getOrderQuantity());
      logger.info("Db quantity: " + product.getQuantity());
      product.setStock(product.getQuantity() - item.getOrderQuantity());
      productRepository.save(product);

    }
  }

  public void releaseInventory(Order order) {
    for (OrderItems item : order.getItems()) {
      OrderItem product = item.getProduct();
      product.setStock(product.getQuantity() + item.getOrderQuantity());
    }
  }
}
