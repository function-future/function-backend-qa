package com.future.function.qa.api.scoring.assignment;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.assignment.AssignmentWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

public class AssignmentAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode) {

    base = super.prepare()
        .basePath(String.format(Path.ASSIGNMENT, batchCode));
    return base;
  }

  @Step
  public Response createAssignment(AssignmentWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(AssignmentWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response createWithoutCookie(AssignmentWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

}
