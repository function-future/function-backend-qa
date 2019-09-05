package com.future.function.qa.model.response.base;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends BaseResponse {

  private Map<String, List<String>> errors;

  public ErrorResponse() {

  }

  @Builder
  private ErrorResponse(int code, String status, Map<String, List<String>> errors) {

    super(code, status);
    this.errors = errors;
  }
}
