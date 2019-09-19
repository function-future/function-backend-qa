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

  @Step
  public Response getAllQuestion(Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getAllWithCookie(cookie),
        () -> getAllWithoutCookie(cookie));
  }

  private Response getAllWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .get();
  }

  private Response getAllWithoutCookie(Cookie cookie) {

    return base.get();
  }

  @Step
  public Response getQuestion(String id, Cookie cookie) {

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
  public Response updateQuestion(String id, QuestionWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> updateWithCookie(id, request, cookie),
        () -> updateWithoutCookie(id, request));
  }

  private Response updateWithCookie(String id, QuestionWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  private Response updateWithoutCookie(String id, QuestionWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .put(String.format(PATH_ID, id));
  }

  @Step
  public Response deleteQuestion(String id, Cookie cookie) {

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
