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
  public Response delete(String id, Cookie cookie) {

    return doByCookiePresent(cookie, deleteAnnouncementWithCookie(id, cookie), deleteAnnouncementWithoutCookie(id));
  }

  private Supplier<Response> deleteAnnouncementWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
        .delete(String.format(PATH_ID, id));
  }

  private Supplier<Response> deleteAnnouncementWithoutCookie(String id) {

    return () -> base.delete(String.format(PATH_ID, id));
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getAnnouncementsWithCookie(page, size, cookie),
        getAnnouncementsWithoutCookie(page, size));
  }

  private Supplier<Response> getAnnouncementsWithCookie(int page, int size, Cookie cookie) {

    return () -> base.queryParam("page", page)
        .queryParam("size", size)
        .cookie(cookie)
        .get();
  }

  private Supplier<Response> getAnnouncementsWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    return doByCookiePresent(cookie, getAnnouncementDetailWithCookie(id, cookie),
        getAnnouncementDetailWithoutCookie(id));
  }

  private Supplier<Response> getAnnouncementDetailWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getAnnouncementDetailWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(ANNOUNCEMENT);

    return base;
  }

  @Step
  public Response update(String id, AnnouncementWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, updateAnnouncementWithCookie(id, request, cookie),
        updateAnnouncementWithoutCookie(id, request));
  }

  private Supplier<Response> updateAnnouncementWithCookie(String id, AnnouncementWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .put(String.format(PATH_ID, id));
  }

  private Supplier<Response> updateAnnouncementWithoutCookie(String id, AnnouncementWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .put(String.format(PATH_ID, id));
  }
}
