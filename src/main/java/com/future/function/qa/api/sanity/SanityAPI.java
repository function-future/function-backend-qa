package com.future.function.qa.api.sanity;

import org.springframework.stereotype.Component;

import com.future.function.qa.api.BaseAPI;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

@Component
public class SanityAPI extends BaseAPI {

  private RequestSpecification base;

  @Step
  public Response get() {

    return base.get();
  }

  @Step
  public RequestSpecification prepare() {

    base = super.prepare()
        .baseUri("https://www.google.com");

    return base;
  }
}
