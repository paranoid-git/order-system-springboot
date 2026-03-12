package com.order.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRegister {
  private String email;
  private String name;
  private String phone;
  private String password;

  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.name;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getPassword() {
    return this.password;
  }
}
