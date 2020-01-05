package com.future.function.qa.api.core.course;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;

import static com.future.function.qa.util.Path.COURSE;

public class CourseAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(COURSE);

    return base;
  }

}
