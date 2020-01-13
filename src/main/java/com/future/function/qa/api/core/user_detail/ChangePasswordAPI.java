package com.future.function.qa.api.core.user_detail;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.user_detail.ChangePasswordWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.CHANGE_PASSWORD;

public class ChangePasswordAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(CHANGE_PASSWORD);

    return base;
  }

  @Step
  public Response put(ChangePasswordWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, changePasswordWithCookie(request, cookie),
                             changePasswordWithoutCookie(request)
    );
  }

  private Supplier<Response> changePasswordWithCookie(
    ChangePasswordWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .put();
  }

  private Supplier<Response> changePasswordWithoutCookie(
    ChangePasswordWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put();
  }

}
