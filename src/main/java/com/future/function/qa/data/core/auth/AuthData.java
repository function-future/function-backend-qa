package com.future.function.qa.data.core.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.auth.AuthWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.auth.AuthWebResponse;
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

  public void setResponse(Response response) {

    super.setResponse(response);
    this.response = asDataResponse(response, new TypeReference<DataResponse<AuthWebResponse>>() {});
  }
}
