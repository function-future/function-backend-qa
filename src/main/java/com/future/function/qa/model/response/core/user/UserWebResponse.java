package com.future.function.qa.model.response.core.user;

import com.future.function.qa.model.response.core.batch.BatchWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWebResponse {

  private String address;

  private String avatar;

  private String avatarId;

  private BatchWebResponse batch;

  private String email;

  private Integer finalPoint;

  private String id;

  private String name;

  private String phone;

  private String role;

  private String university;
}
