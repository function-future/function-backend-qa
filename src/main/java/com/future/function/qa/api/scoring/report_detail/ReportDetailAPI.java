package com.future.function.qa.api.scoring.report_detail;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.report_detail.ReportDetailScoreWebRequest;
import com.future.function.qa.model.request.scoring.report_detail.ScoreStudentWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class ReportDetailAPI extends BaseAPI {

  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(Path.SUMMARY_WITHOUT_ID);
    return base;
  }

  @Step
  public Response giveScoreToStudents(ScoreStudentWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> giveScoreWithCookie(request, cookie),
        () -> giveScoreWithoutCookie(request));
  }

  private Response giveScoreWithCookie(ScoreStudentWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response giveScoreWithoutCookie(ScoreStudentWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

}
