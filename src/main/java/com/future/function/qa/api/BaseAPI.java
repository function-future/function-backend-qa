package com.future.function.qa.api;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import com.future.function.qa.FunctionQAApplication;
import com.future.function.qa.properties.FunctionProperties;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.steps.ScenarioSteps;

@ContextConfiguration(classes = FunctionQAApplication.class,
    initializers = ConfigFileApplicationContextInitializer.class)
public abstract class BaseAPI extends ScenarioSteps {

  protected static final String PATH_ID = "/%s";

  protected RequestSpecification base;

  @Autowired
  private FunctionProperties functionProperties;

  protected Response doByCookiePresent(Cookie cookie, Supplier<Response> supplierWithCookie,
      Supplier<Response> supplierWithoutCookie) {

    if (cookie == null) {
      return supplierWithoutCookie.get();
    }

    return supplierWithoutCookie.get();
  }

  public String getCookieName() {

    return functionProperties.getCookieName();
  }

  public RequestSpecification prepare() {

    return SerenityRest.given()
        .baseUri(String.format("%s:%s", functionProperties.getHost(), functionProperties.getPort()))
        .log()
        .all();
  }
}
