package com.future.function.qa.api.core.user;

import static com.future.function.qa.util.Path.USER;

import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class UserAPI extends BaseAPI {

  @Step
  public Response createUser(UserWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createUserWithCookie(request, cookie), createUserWithoutCookie(request));
  }

  private Supplier<Response> createUserWithCookie(UserWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createUserWithoutCookie(UserWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(USER);

    return base;
  }
}
