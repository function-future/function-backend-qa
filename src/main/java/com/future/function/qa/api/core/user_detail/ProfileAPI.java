package com.future.function.qa.api.core.user_detail;

import com.future.function.qa.api.BaseAPI;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.PROFILE;

public class ProfileAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(PROFILE);

    return base;
  }

  @Step
  public Response get(Cookie cookie) {

    return doByCookiePresent(
      cookie, getProfileWithCookie(cookie), getProfileWithoutCookie());
  }

  private Supplier<Response> getProfileWithCookie(Cookie cookie) {

    return () -> base.cookie(cookie)
      .get();
  }

  private Supplier<Response> getProfileWithoutCookie() {

    return () -> base.get();
  }

}
