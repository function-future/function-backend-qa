package com.future.function.qa.api.batch;

import static com.future.function.qa.util.Path.BATCH;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class BatchAPI extends BaseAPI {

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(BATCH);

    return base;
  }
}
