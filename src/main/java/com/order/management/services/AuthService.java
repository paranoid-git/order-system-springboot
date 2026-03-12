package com.order.management.services;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.order.management.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.order.management.dto.PostRegister;
import com.order.management.repository.UserRepository;
import com.order.management.util.JWTUtil;
import com.order.management.util.ResponseUtil;
import com.order.management.dto.PostLogin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
  private static final ResponseUtil responseUtil = new ResponseUtil();

  private final JWTUtil jwtUtil = new JWTUtil();
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  @Autowired
  private UserRepository userRepository;

  public ResponseEntity<?> login(PostLogin requestBody) {
    if (!encoder.matches(requestBody.getPassword(),
        userRepository.findByEmail(requestBody.getEmail()).get().getPassword())) {
      return responseUtil.makeResponse("Email or password is incorrect.", HttpStatus.UNAUTHORIZED);
    }
    String token = jwtUtil.generateToken(requestBody.getEmail());
    Map<String, Object> value = new HashMap<>();
    value.put("token", token);
    ResponseEntity<?> response = responseUtil.makeResponse("Successfully logged in.", HttpStatus.OK, value);
    response.getHeaders().add("Authorization", "Bearer " + token);
    return response;
  }

  public ResponseEntity<?> register(PostRegister requestBody) {
    String hashedPassword = encoder.encode(requestBody.getPassword());
    User user = new User();
    if (userRepository.findByEmail(requestBody.getEmail()).isEmpty()) {
      user.setEmail(requestBody.getEmail());
      user.setName(requestBody.getName());
      user.setPhone(requestBody.getPhone());
      user.setPassword(hashedPassword);
      userRepository.save(user);
      return responseUtil.makeResponse("Successfully created your account, please log in.", HttpStatus.CREATED);
    } else {
      return responseUtil.makeResponse("Email already linked to another account.", HttpStatus.CONFLICT);
    }
  }
}
