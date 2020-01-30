package com.future.function.qa.data.core.user_detail;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.user_detail.ChangePasswordWebRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordData extends BaseData {

  private ChangePasswordWebRequest request;

  public ChangePasswordWebRequest createRequest(
    String oldPassword, String newPassword
  ) {

    request = ChangePasswordWebRequest.builder()
      .oldPassword(oldPassword)
      .newPassword(newPassword)
      .build();

    return request;
  }

}
