package com.future.function.qa.api.question_bank;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.question_bank.QuestionBankWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.springframework.stereotype.Component;

public class QuestionBankAPI extends BaseAPI {

  @Override
  @Step
  public RequestSpecification prepare() {
    base = super.prepare()
        .basePath(Path.QUESTION_BANK);
    return base;
  }

  @Step
  public Response createQuestionBank(QuestionBankWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(QuestionBankWebRequest request, Cookie cookie) {
    return base
        .cookie(cookie)
        .body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  private Response createWithoutCookie(QuestionBankWebRequest request) {
    return base
        .body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response getQuestionBank(String id, Cookie cookie) {
    return doByCookiePresent(cookie,
        () -> getWithCookie(id, cookie),
        () -> getWithoutCookie(id));
  }

  private Response getWithCookie(String id, Cookie cookie) {
    return base
        .cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Response getWithoutCookie(String id) {
    return base.get(String.format(PATH_ID, id));
  }

  @Step
  public Response getAllQuestionBank(Cookie cookie) {
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
}
