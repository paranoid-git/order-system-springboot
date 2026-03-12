package com.order.management.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.Date;

@Component
public class ResponseUtil {
  private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

  public ResponseEntity<?> makeResponse(String message, HttpStatus status, Object data) {
    Map<String, Object> value = new HashMap<>();
    value.put("message", message);
    value.put("timestamp", new Date());
    value.put("data", data);
    logger.info(value.toString());
    ResponseEntity response = new ResponseEntity(value, status);
    return response;
  }

  public ResponseEntity<?> makeResponse(String message, HttpStatus status) {
    Map<String, Object> value = new HashMap<>();
    value.put("message", message);
    value.put("timestamp", new Date());
    logger.info(value.toString());
    ResponseEntity response = new ResponseEntity(value, status);
    return response;
  }
}
