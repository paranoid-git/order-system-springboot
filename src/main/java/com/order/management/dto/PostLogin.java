package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLogin {
  private String email;
  private String password;

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }
}
