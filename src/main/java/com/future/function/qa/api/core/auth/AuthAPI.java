package com.future.function.qa.api.core.auth;

import static com.future.function.qa.util.Path.AUTH;

import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.auth.AuthWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class AuthAPI extends BaseAPI {

  @Step
  public Response getLoginStatus(Cookie cookie) {

    return doByCookiePresent(cookie, getLoginStatusWithCookie(cookie), getLoginStatusWithoutCookie());
  }

  private Supplier<Response> getLoginStatusWithCookie(Cookie cookie) {

    return () -> base.cookie(cookie)
        .get();
  }

  private Supplier<Response> getLoginStatusWithoutCookie() {

    return () -> base.get();
  }

  @Step
  public Response login(AuthWebRequest request) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response logout(Cookie cookie) {

    return doByCookiePresent(cookie, logoutWithCookie(cookie), logoutWithoutCookie());
  }

  private Supplier<Response> logoutWithCookie(Cookie cookie) {

    return () -> base.cookie(cookie)
        .delete();
  }

  private Supplier<Response> logoutWithoutCookie() {

    return () -> base.delete();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(AUTH);

    return base;
  }
}
