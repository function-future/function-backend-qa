package com.future.function.qa.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.future.function.qa.model.response.base.BaseResponse;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public abstract class BaseData {

  protected BaseResponse baseResponse = new BaseResponse();

  protected Cookie cookie;

  protected ErrorResponse errorResponse = new ErrorResponse();

  @Autowired
  protected ObjectMapper mapper;

  protected int responseCode;

  public <T> DataResponse<T> asDataResponse(Response response, TypeReference<DataResponse<T>> typeReference) {

    try {
      return mapper.readValue(response.print(), typeReference);
    } catch (Exception e) {
      return new DataResponse<>();
    }
  }

  public <T> PagingResponse<T> asPagingResponse(Response response, TypeReference<PagingResponse<T>> typeReference) {

    try {
      return mapper.readValue(response.print(), typeReference);
    } catch (Exception e) {
      return new PagingResponse<>();
    }
  }

  public void setResponse(Response response) {

    this.responseCode = response.getStatusCode();
    this.baseResponse = asBaseResponse(response);
    this.errorResponse = asErrorResponse(response);
  }

  public BaseResponse asBaseResponse(Response response) {

    try {
      return response.as(BaseResponse.class);
    } catch (Exception e) {
      return new BaseResponse();
    }
  }

  public ErrorResponse asErrorResponse(Response response) {

    try {
      return response.as(ErrorResponse.class);
    } catch (Exception e) {
      return new ErrorResponse();
    }
  }
}
