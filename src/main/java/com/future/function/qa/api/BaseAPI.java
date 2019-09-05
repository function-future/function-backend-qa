package com.future.function.qa.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import com.future.function.qa.FunctionQAApplication;
import com.future.function.qa.properties.FunctionProperties;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.steps.ScenarioSteps;

@ContextConfiguration(classes = FunctionQAApplication.class,
    initializers = ConfigFileApplicationContextInitializer.class)
public abstract class BaseAPI extends ScenarioSteps {

  @Autowired
  private FunctionProperties functionProperties;

  protected RequestSpecification base;

  protected RequestSpecification prepare() {

    return SerenityRest.given()
        .baseUri(String.format("%s:%s", functionProperties.getHost(), functionProperties.getPort()))
        .log()
        .all();
  }

  public String getCookieName() {

    return functionProperties.getCookieName();
  }
}
