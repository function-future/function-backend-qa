package com.future.function.qa.model.request.core.user_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordWebRequest {

  private String oldPassword;

  private String newPassword;

}
