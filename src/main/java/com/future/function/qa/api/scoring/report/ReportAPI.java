package com.future.function.qa.api.scoring.report;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.report.ReportWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class ReportAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode) {

    base = super.prepare()
        .basePath(String.format(Path.REPORT, batchCode));
    return base;
  }

  @Step
  public Response createReport(ReportWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(ReportWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response createWithoutCookie(ReportWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  @Step
  public Response getStudentsWithinBatch(Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getStudentsWithCookie(cookie),
        this::getStudentsWithoutCookie);
  }

  private Response getStudentsWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .get(Path.STUDENTS);
  }

  private Response getStudentsWithoutCookie() {

    return base.get(Path.STUDENTS);
  }

  @Step
  public Response getReport(String id, Cookie cookie) {

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
  public Response getAllReport(Cookie cookie) {

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
  public Response updateReport(String id, ReportWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> updateWithCookie(id, request, cookie),
        () -> updateWithoutCookie(id, request));
  }

  private Response updateWithCookie(String id, ReportWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  private Response updateWithoutCookie(String id, ReportWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  @Step
  public Response deleteReport(String id, Cookie cookie) {

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
