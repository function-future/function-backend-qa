package com.future.function.qa.api.scoring.report;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.report.ReportWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

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

}
