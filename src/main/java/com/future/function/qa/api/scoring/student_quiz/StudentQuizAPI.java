package com.future.function.qa.api.scoring.student_quiz;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.util.Path;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class StudentQuizAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode, String quizId) {

    base = super.prepare()
        .basePath(String.format(Path.STUDENT_QUIZ, batchCode, quizId));
    return base;
  }

  @Step
  public Response getOrCreateStudentQuiz(Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> getOrCreateWithCookie(cookie),
        this::getOrCreateWithoutCookie);
  }

  private Response getOrCreateWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .post();
  }

  private Response getOrCreateWithoutCookie() {

    return base.post();
  }

}
