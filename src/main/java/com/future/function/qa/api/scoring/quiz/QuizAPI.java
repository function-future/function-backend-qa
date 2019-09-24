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

  @Step
  public Response getAllQuiz(Cookie cookie) {

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
  public Response getQuiz(String id, Cookie cookie) {

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
  public Response updateQuiz(String id, QuizWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> updateWithCookie(id, request, cookie),
        () -> updateWithoutCookie(id, request));
  }

  private Response updateWithCookie(String id, QuizWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  private Response updateWithoutCookie(String id, QuizWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }
}
