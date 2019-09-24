package com.future.function.qa.data.auth;

import org.springframework.stereotype.Component;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.auth.AuthWebRequest;
import com.future.function.qa.model.response.auth.AuthWebResponse;
import com.future.function.qa.model.response.base.DataResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthData extends BaseData {

  private AuthWebRequest request;

  private DataResponse<AuthWebResponse> response;

  public AuthWebRequest createRequest(String email, String password) {

    request = AuthWebRequest.builder()
        .email(email)
        .password(password)
        .build();

    return request;
  }

  public void setResponse(DataResponse<AuthWebResponse> response) {

    this.response = response;
    this.responseCode = response.getCode();
  }
}
