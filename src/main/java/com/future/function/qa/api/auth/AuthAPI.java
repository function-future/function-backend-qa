package com.future.function.qa.api.auth;

import static com.future.function.qa.util.Path.AUTH;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.auth.AuthWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class AuthAPI extends BaseAPI {

  @Step
  public Response getLoginStatus(Cookie cookie) {

    if (cookie == null) {
      return getLoginStatusWithoutCookie();
    }

    return getLoginStatusWithCookie(cookie);
  }

  private Response getLoginStatusWithCookie(Cookie cookie) {

    return base.cookie(cookie)
        .get();
  }

  private Response getLoginStatusWithoutCookie() {

    return base.get();
  }

  @Step
  public Response login(AuthWebRequest request) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response logout(Cookie cookie) {

    return base.cookie(cookie)
        .delete();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(AUTH);

    return base;
  }
}
