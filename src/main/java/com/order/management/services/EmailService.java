package com.order.management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

import com.order.management.model.Order;
import com.order.management.model.User;

@Service
public class EmailService {
  @Autowired
  private MailSender mailSender;

  public void sendOrderConfirmation(Order order, User user) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("Order confirmation");
    message.setText("Order confirmation for OrderId: " + order.getOrderId());
    mailSender.send(message);
  }
}
