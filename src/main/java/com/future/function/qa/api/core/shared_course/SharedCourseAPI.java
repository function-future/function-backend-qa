package com.future.function.qa.api.core.shared_course;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.shared_course.SharedCourseWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.SHARED_COURSE;

public class SharedCourseAPI extends BaseAPI {

  public void prepare(String batchCode) {

    base = super.prepare()
      .basePath(String.format(SHARED_COURSE, batchCode));
  }

  @Step
  public Response create(
    String targetBatchCode, SharedCourseWebRequest request, Cookie cookie
  ) {

    return doByCookiePresent(
      cookie,
      this.createSharedCourseWithCookie(targetBatchCode, request, cookie),
      this.createSharedCourseWithoutCookie(targetBatchCode, request)
    );
  }

  private Supplier<Response> createSharedCourseWithCookie(
    String targetBatchCode, SharedCourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  private Supplier<Response> createSharedCourseWithoutCookie(
    String targetBatchCode, SharedCourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

}