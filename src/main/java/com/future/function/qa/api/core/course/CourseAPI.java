package com.future.function.qa.api.core.course;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.course.CourseWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.COURSE;

public class CourseAPI extends BaseAPI {

  @Step
  public Response create(CourseWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createCourseWithCookie(request, cookie),
                             createCourseWithoutCookie(request)
    );
  }

  private Supplier<Response> createCourseWithoutCookie(
    CourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  private Supplier<Response> createCourseWithCookie(
    CourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(COURSE);

    return base;
  }

}
