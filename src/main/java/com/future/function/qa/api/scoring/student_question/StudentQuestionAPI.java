package com.future.function.qa.api.scoring.student_question;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.util.Path;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class StudentQuestionAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode, String quizId) {

    base = super.prepare()
        .basePath(String.format(Path.STUDENT_QUESTION, batchCode, quizId));
    return base;
  }

  @Step
  public Response findAllUnansweredQuestion(Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> findAllUnansweredQuestionWithCookie(cookie),
        this::findAllUnansweredQuestionWithoutCookie);
  }

  private Response findAllUnansweredQuestionWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .get();
  }

  private Response findAllUnansweredQuestionWithoutCookie() {

    return base.get();
  }

}
