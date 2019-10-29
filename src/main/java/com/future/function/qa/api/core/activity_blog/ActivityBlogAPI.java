package com.future.function.qa.api.core.activity_blog;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.activity_blog.ActivityBlogWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.ACTIVITY_BLOG;

public class ActivityBlogAPI extends BaseAPI {

  @Step
  public Response create(ActivityBlogWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             createActivityBlogWithCookie(request, cookie),
                             createActivityBlogWithoutCookie(request)
    );
  }

  private Supplier<Response> createActivityBlogWithCookie(
    ActivityBlogWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  private Supplier<Response> createActivityBlogWithoutCookie(
    ActivityBlogWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(ACTIVITY_BLOG);

    return base;
  }

}
