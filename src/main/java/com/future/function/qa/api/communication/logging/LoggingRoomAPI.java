package com.future.function.qa.api.communication.logging;

/**
 * Author: RickyKennedy
 * Created At:11:15 AM 1/31/2020
 */
import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class LoggingRoomAPI extends BaseAPI {

  @Step
  public Response getLoggingRooms(String role, int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getUsersWithCookie(role, page, size, cookie),
      getUsersWithoutCookie(role, page, size));
  }

  private Supplier<Response> getUsersWithCookie(String role, int page, int size, Cookie cookie) {

    return () -> base.cookie(cookie)
      .queryParam("role", role)
      .queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  private Supplier<Response> getUsersWithoutCookie(String role, int page, int size) {

    return () -> base.queryParam("role", role)
      .queryParam("page", page)
      .queryParam("size", size)
      .get();
  }
}
