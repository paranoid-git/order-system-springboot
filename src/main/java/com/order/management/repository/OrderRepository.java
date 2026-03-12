package com.order.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.management.dto.OrderDTO;
import com.order.management.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findById(Long id);
}
