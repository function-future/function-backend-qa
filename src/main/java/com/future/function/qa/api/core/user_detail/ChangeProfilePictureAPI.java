package com.future.function.qa.api.core.user_detail;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.user_detail.ChangeProfilePictureWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.CHANGE_PROFILE_PICTURE;

public class ChangeProfilePictureAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(CHANGE_PROFILE_PICTURE);

    return base;
  }

  @Step
  public Response put(ChangeProfilePictureWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             changeProfilePictureWithCookie(request, cookie),
                             changeProfilePictureWithoutCookie(request)
    );
  }

  private Supplier<Response> changeProfilePictureWithCookie(
    ChangeProfilePictureWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .put();
  }

  private Supplier<Response> changeProfilePictureWithoutCookie(
    ChangeProfilePictureWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put();
  }

}
