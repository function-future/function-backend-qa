package com.future.function.qa.api.scoring.summary;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.util.Path;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class SummaryAPI extends BaseAPI {

  public RequestSpecification prepare(String studentId) {

    base = super.prepare()
        .basePath(String.format(Path.SUMMARY, studentId));
    return base;
  }

  @Step
  public Response getStudentSummary(String type, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getWithCookie(type, cookie),
        () -> getWithoutCookie(type));
  }

  private Response getWithCookie(String type, Cookie cookie) {

    return base.cookie(cookie)
        .param("type", type)
        .get();
  }

  private Response getWithoutCookie(String type) {

    return base
        .param("type", type)
        .get();
  }

}
