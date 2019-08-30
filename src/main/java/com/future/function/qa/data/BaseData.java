package com.future.function.qa.data;

import com.future.function.qa.model.response.base.BaseResponse;
import com.future.function.qa.model.response.base.DataResponse;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import lombok.Data;

@Data
public abstract class BaseData {

  protected BaseResponse baseResponse;

  protected Cookie cookie;

  protected int responseCode;

  public static BaseResponse asBaseResponse(Response response) {

    return response.as(BaseResponse.class);
  }

  public static DataResponse asDataResponse(Response response) {

    return response.as(DataResponse.class);
  }
}
