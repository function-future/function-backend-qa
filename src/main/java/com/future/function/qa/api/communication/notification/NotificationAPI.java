package com.future.function.qa.api.communication.notification;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.communication.chatroom.ChatroomWebRequest;
import com.future.function.qa.model.request.communication.notification.NotificationWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

public class NotificationAPI extends BaseAPI {

  @Override
  @Step
  public RequestSpecification prepare() {
    base = super.prepare().basePath(Path.NOTIFICATIONS);
    return base;
  }

  @Step
  public Response create(NotificationWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, createNotificationWithCookie(request, cookie),
            createNotificationWithoutCookie(request));
  }

  private Supplier<Response> createNotificationWithCookie(NotificationWebRequest request, Cookie cookie) {
    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createNotificationWithoutCookie(NotificationWebRequest request) {
    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response getAll(Cookie cookie) {
    return doByCookiePresent(cookie, getNotificationWithCookie(cookie),
            getNotificationWithoutCookie());
  }

  private Supplier<Response> getNotificationWithCookie(Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get();
  }

  private Supplier<Response> getNotificationWithoutCookie() {
    return () -> base.get();
  }

  @Step
  public Response getUnseen(Cookie cookie) {
    return doByCookiePresent(cookie, getUnseenWithCookie(cookie),
            getUnseenWithoutCookie());
  }

  private Supplier<Response> getUnseenWithCookie(Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get("/_unseen_total");
  }

  private Supplier<Response> getUnseenWithoutCookie() {
    return () -> base.get("/_unseen_total");
  }

  @Step
  public Response read(String notificationId, Cookie cookie) {
    return doByCookiePresent(cookie, readWithCookie(notificationId, cookie),
            readWithoutCookie(notificationId));
  }

  private Supplier<Response> readWithCookie(String notificationId, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .put(String.format(PATH_ID + "/_read", notificationId));
  }

  private Supplier<Response> readWithoutCookie(String notificationId) {
    return () -> base.put(String.format(PATH_ID + "/_read", notificationId));
  }
}
