package com.future.function.qa.model.request.core.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWebRequest {

  private String address;

  private List<String> avatar;

  private String batch;

  private String email;

  private String id;

  private String name;

  private String phone;

  private String role;

  private String university;
}

