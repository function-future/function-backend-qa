package com.future.function.qa.api.core.announcement;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.announcement.AnnouncementWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.ANNOUNCEMENT;

public class AnnouncementAPI extends BaseAPI {

  @Step
  public Response create(AnnouncementWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createAnnouncementWithCookie(request, cookie),
        createAnnouncementWithoutCookie(request));
  }

  private Supplier<Response> createAnnouncementWithCookie(AnnouncementWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createAnnouncementWithoutCookie(AnnouncementWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(ANNOUNCEMENT);

    return base;
  }
}
