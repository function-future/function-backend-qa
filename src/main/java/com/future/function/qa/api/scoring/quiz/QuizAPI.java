package com.future.function.qa.api.scoring.quiz;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.quiz.QuizWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class QuizAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode) {
    base = super.prepare().basePath(String.format(Path.QUIZ, batchCode));
    return base;
  }

  @Step
  public Response createQuiz(QuizWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(QuizWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response createWithoutCookie(QuizWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }
}
