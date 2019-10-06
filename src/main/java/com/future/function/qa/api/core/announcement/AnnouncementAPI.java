package com.future.function.qa.api.core.announcement;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import static com.future.function.qa.util.Path.ANNOUNCEMENT;

public class AnnouncementAPI extends BaseAPI {

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(ANNOUNCEMENT);

    return base;
  }

}
