package com.order.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.order.management.model.OrderItem;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<OrderItem, Long> {
  Optional<OrderItem> findById(Long id);
}
