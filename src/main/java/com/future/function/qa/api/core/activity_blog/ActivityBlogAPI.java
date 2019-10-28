package com.future.function.qa.api.core.activity_blog;

import com.future.function.qa.api.BaseAPI;
import io.restassured.specification.RequestSpecification;

import static com.future.function.qa.util.Path.ACTIVITY_BLOG;

public class ActivityBlogAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(ACTIVITY_BLOG);

    return base;
  }
}
