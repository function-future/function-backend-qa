package com.future.function.qa.data.core.user_detail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import io.restassured.response.Response;
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
public class ProfileData extends BaseData {

  private DataResponse<UserWebResponse> retrievedResponse =
    new DataResponse<>();

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.retrievedResponse = asDataResponse(
      response, new TypeReference<DataResponse<UserWebResponse>>() {});
  }

}
