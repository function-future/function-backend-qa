package com.future.function.qa.api.scoring.report_detail;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.report_detail.ReportDetailScoreWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class ReportDetailAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode, String reportId) {

    base = super.prepare()
        .basePath(String.format(Path.REPORT_DETAIL, batchCode, reportId));
    return base;
  }

  @Step
  public Response giveScoreToStudents(ReportDetailScoreWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> giveScoreWithCookie(request, cookie),
        () -> giveScoreWithoutCookie(request));
  }

  private Response giveScoreWithCookie(ReportDetailScoreWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response giveScoreWithoutCookie(ReportDetailScoreWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

}
