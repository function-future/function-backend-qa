package com.future.function.qa.api.core.resource;

import static com.future.function.qa.util.Path.RESOURCE;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class ResourceAPI extends BaseAPI {

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(RESOURCE);

    return base;
  }
}
