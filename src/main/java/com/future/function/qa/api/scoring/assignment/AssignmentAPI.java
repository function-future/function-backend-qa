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

  @Step
  public Response getAllAssignment(Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getAllWithCookie(cookie),
        this::getAllWithoutCookie);
  }

  private Response getAllWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .get();
  }

  private Response getAllWithoutCookie() {

    return base.get();
  }

  @Step
  public Response getAssignment(String id, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getWithCookie(id, cookie),
        () -> getWithoutCookie(id));
  }

  private Response getWithCookie(String id, Cookie cookie) {

    return base.cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Response getWithoutCookie(String id) {

    return base.get(String.format(PATH_ID, id));
  }

  @Step
  public Response updateAssignment(String id, AssignmentWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> updateWithCookie(id, request, cookie),
        () -> updateWithoutCookie(id, request));
  }

  private Response updateWithCookie(String id, AssignmentWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  private Response updateWithoutCookie(String id, AssignmentWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  @Step
  public Response deleteAssignment(String id, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> deleteWithCookie(id, cookie),
        () -> deleteWithoutCookie(id));
  }

  private Response deleteWithCookie(String id, Cookie cookie) {

    return base.cookie(cookie)
        .delete(String.format(PATH_ID, id));
  }

  private Response deleteWithoutCookie(String id) {

    return base.delete(String.format(PATH_ID, id));
  }

}
