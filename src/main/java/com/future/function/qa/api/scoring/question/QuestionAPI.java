package com.future.function.qa.api.scoring.question;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.question.QuestionWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class QuestionAPI extends BaseAPI {

  @Step
  public RequestSpecification prepare(String questionBankId) {

    base = super.prepare().basePath(String.format(Path.QUESTION_BANK + PATH_ID + Path.QUESTION, questionBankId));
    return base;
  }

  @Step
  public Response createQuestion(QuestionWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(QuestionWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  private Response createWithoutCookie(QuestionWebRequest request) {

    return base
        .body(request)
        .contentType(ContentType.JSON)
        .post();
  }
}
