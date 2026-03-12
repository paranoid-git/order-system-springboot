package com.order.management.services;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.order.management.dto.PostOrder;
import com.order.management.repository.OrderRepository;
import com.order.management.repository.ProductRepository;
import com.order.management.repository.UserRepository;
import com.order.management.util.ResponseUtil;
import com.order.management.model.User;
import org.json.simple.JSONObject;
import com.order.management.dto.OrderDTO;
import com.order.management.dto.OrderItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import com.order.management.model.Order;
import com.order.management.exceptions.OrderProcessingException;
import com.order.management.exceptions.OrderValidationException;
import com.order.management.model.Order;
import com.order.management.model.OrderItem;
import com.order.management.model.OrderItems;

import org.slf4j.Logger;
import java.util.List;

@Service
public class OrderService {
  private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private final ModelMapper modelMapper;

  private static final ResponseUtil responseUtil = new ResponseUtil();

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private OrderProcessingService processingService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderRepository orderRepository;

  public List<OrderItems> convertToEntities(List<OrderItemRequest> itemDTOs) {

    return itemDTOs.stream()
        .map(dto -> {
          logger.info("Looking for productId: " + dto.getProductId());
          OrderItem dbItem = productRepository.findById(dto.getProductId())
              .orElseThrow(() -> new OrderProcessingException("Item not found in database: " + dto.getProductId()));
          OrderItems orderItems = new OrderItems();
          orderItems.setProduct(dbItem);
          orderItems.setOrderQuantity(dto.getQuantity()); // ← quantity customer wants to buy
          return orderItems;
        })
        .collect(Collectors.toList());
  }

  public OrderService(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public ResponseEntity<?> createOrder(PostOrder requestBody) {
    Order order = new Order();
    logger.info("Creating order.");

    List<OrderItemRequest> dto = requestBody.getItems();
    List<OrderItems> items = convertToEntities(dto);
    order.setItems(items);
    order.setTotalAmount(calculateSum(items));
    order.setDate(LocalDate.now());
    order.setStatus("PENDING");
    order.setUserId(requestBody.getUserId());
    order.setPaymentMethod(requestBody.getPaymentMethod());
    order.setShippingAddress(requestBody.getShippingAddress());
    order.setShippingMethod(requestBody.getShippingMethod());
    orderRepository.save(order);
    processingService.processOrderAsync(order.getOrderId());
    return responseUtil.makeResponse("Order Created Successfully! Processing will begin shortly.", HttpStatus.CREATED,
        order);
  }

  public Double calculateSum(List<OrderItems> items) {
    Double totalSum = 0.00;
    for (OrderItems item : items) {
      totalSum += item.getProduct().getPrice();
      logger.info("New Total Sum: " + totalSum);
    }
    return totalSum;
  }

  public void validateOrder(Order order) {
    User user = userRepository.findById(order.getUserId())
        .orElseThrow(() -> new OrderProcessingException("UserId not found."));

    for (OrderItems item : order.getItems()) {
      OrderItem product = item.getProduct();

      logger.info("Product: " + product.getName());
      logger.info("Stock: " + product.getQuantity());
      logger.info("Requested: " + item.getOrderQuantity());

      if (product.getQuantity() < item.getOrderQuantity()) {
        throw new OrderValidationException(
            "Insufficient stock for: " + product.getName() +
                ". Available: " + product.getQuantity() +
                ", Requested: " + item.getOrderQuantity(),
            order);
      }
    }
  }
}
