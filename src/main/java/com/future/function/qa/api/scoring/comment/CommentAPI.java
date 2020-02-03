package com.future.function.qa.api.scoring.comment;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.scoring.comment.CommentWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class CommentAPI extends BaseAPI {

  public RequestSpecification prepare(String batchCode, String studentId, String assignmentId) {

    base = super.prepare()
        .basePath(String.format(Path.COMMENT, batchCode, assignmentId, studentId));
    return base;
  }

  @Step
  public Response createComment(CommentWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
        () -> createWithCookie(request, cookie),
        () -> createWithoutCookie(request));
  }

  private Response createWithCookie(CommentWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  private Response createWithoutCookie(CommentWebRequest request) {

    return base
        .contentType(ContentType.JSON)
        .body(request)
        .post();
  }

  @Step
  public Response getAllComment(Cookie cookie) {

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
