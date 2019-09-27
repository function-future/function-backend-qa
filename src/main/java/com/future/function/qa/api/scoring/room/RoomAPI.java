package com.future.function.qa.api.scoring.room;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.util.Path;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class RoomAPI extends BaseAPI {

  public RequestSpecification prepare(String assignmentId, String studentId) {

    base = super.prepare()
        .basePath(String.format(Path.ROOM, assignmentId, studentId));
    return base;
  }

  @Step
  public Response getOrCreateRoom(Cookie cookie) {

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
