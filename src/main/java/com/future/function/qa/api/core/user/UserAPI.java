package com.future.function.qa.api.core.user;

import static com.future.function.qa.util.Path.USER;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class UserAPI extends BaseAPI {

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(USER);

    return base;
  }
}
