package com.order.management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.management.dto.PostLogin;
import com.order.management.dto.PostRegister;
import com.order.management.model.User;
import com.order.management.services.AuthService;
import com.order.management.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody PostLogin requestBody) {
    ResponseEntity<?> response = authService.login(requestBody);

    return response;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody PostRegister requestBody) {
    ResponseEntity<?> response = authService.register(requestBody);
    return response;
  }

}
