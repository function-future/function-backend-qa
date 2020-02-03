package com.future.function.qa.api.communication.reminder;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.communication.notification.NotificationWebRequest;
import com.future.function.qa.model.request.communication.reminder.ReminderWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

public class ReminderAPI extends BaseAPI {

  @Override
  @Step
  public RequestSpecification prepare() {
    base = super.prepare().basePath(Path.REMINDERS);
    return base;
  }

  @Step
  public Response create(ReminderWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, createReminderWithCookie(request, cookie),
            createReminderWithoutCookie(request));
  }

  private Supplier<Response> createReminderWithCookie(ReminderWebRequest request, Cookie cookie) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .cookie(cookie)
            .post();
  }

  private Supplier<Response> createReminderWithoutCookie(ReminderWebRequest request) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .post();
  }

  @Step
  public Response getAll(Cookie cookie) {
    return doByCookiePresent(cookie, getReminderWithCookie(cookie),
            getReminderWithoutCookie());
  }

  private Supplier<Response> getReminderWithCookie(Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get();
  }

  private Supplier<Response> getReminderWithoutCookie() {
    return () -> base.get();
  }

  @Step
  public Response getDetail(String reminderId, Cookie cookie) {
    return doByCookiePresent(cookie, getReminderDetailWithCookie(reminderId, cookie),
            getReminderDetailWithoutCookie(reminderId));
  }

  private Supplier<Response> getReminderDetailWithCookie(String reminderId, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get(String.format(PATH_ID, reminderId));
  }

  private Supplier<Response> getReminderDetailWithoutCookie(String reminderId) {
    return () -> base.get(String.format(PATH_ID, reminderId));
  }

  @Step
  public Response updateReminder(String reminderId, ReminderWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, updateReminderWithCookie(reminderId, request, cookie),
            updateReminderWithoutCookie(reminderId, request));
  }

  private Supplier<Response> updateReminderWithCookie(String reminderId, ReminderWebRequest request, Cookie cookie) {
    return () -> base
            .body(request)
            .contentType(ContentType.JSON)
            .cookie(cookie)
            .put(String.format(PATH_ID, reminderId));
  }

  private Supplier<Response> updateReminderWithoutCookie(String reminderId, ReminderWebRequest request) {
    return () -> base
            .body(request)
            .contentType(ContentType.JSON)
            .put(String.format(PATH_ID, reminderId));
  }

  @Step
  public Response delete(String reminderId, Cookie cookie) {
    return doByCookiePresent(cookie, deleteWithCookie(reminderId, cookie),
            deleteWithoutCookie(reminderId));
  }

  private Supplier<Response> deleteWithCookie(String reminderId, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .delete(String.format(PATH_ID, reminderId));
  }

  private Supplier<Response> deleteWithoutCookie(String reminderId) {
    return () -> base.delete(String.format(PATH_ID, reminderId));
  }

}
