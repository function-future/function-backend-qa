package com.future.function.qa.api.communication.logging;

/**
 * Author: RickyKennedy
 * Created At:11:15 AM 1/31/2020
 */
import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.communication.logging.LogMessageWebRequest;
import com.future.function.qa.model.request.communication.logging.LoggingRoomWebRequest;
import com.future.function.qa.model.request.communication.logging.TopicWebRequest;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class LoggingRoomAPI extends BaseAPI {

  @Override
  @Step
  public RequestSpecification prepare() {
    base = super.prepare()
      .basePath(Path.LOGGINGROOM);
    return base;
  }

  @Step
  public Response getLoggingRooms(Cookie cookie) {

    return doByCookiePresent(cookie,
      () -> base.cookie(cookie).get(),
      () -> base.get());
  }

  @Step
  public Response getLoggingRoom(String loggingRoomId, Cookie cookie) {
    return doByCookiePresent(cookie,
      getLoggingRoomWithCookie(loggingRoomId, cookie),
      getLoggingRoomWithoutCookie(loggingRoomId));
  }

  private Supplier<Response> getLoggingRoomWithCookie(String loggingRoomId, Cookie cookie){
    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID , loggingRoomId));
  }

  private Supplier<Response> getLoggingRoomWithoutCookie(String loggingRoomId){
    return () -> base.get(String.format(PATH_ID , loggingRoomId));
  }

  @Step
  public Response getTopics(String loggingRoomId, Cookie cookie){
    return doByCookiePresent(cookie,
      getTopicsWithCookie(loggingRoomId, cookie),
      getTopicsWithoutCookie(loggingRoomId));
  }

  private Supplier<Response> getTopicsWithCookie(String loggingRoomId, Cookie cookie) {
    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID +"/topics" , loggingRoomId));
  }

  private Supplier<Response> getTopicsWithoutCookie(String loggingRoomId) {
    return () -> base.get(String.format(PATH_ID +"/topics" , loggingRoomId));
  }

  @Step
  public Response getTopic(String loggingRoomId, String topicId, Cookie cookie){
    return doByCookiePresent(cookie,
      getTopicWithCookie(loggingRoomId, topicId, cookie),
      getTopicWithoutCookie(loggingRoomId, topicId));
  }

  private Supplier<Response> getTopicWithCookie(String loggingRoomId, String topicId, Cookie cookie) {
    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID + "/topics" + PATH_ID, loggingRoomId, topicId));
  }

  private Supplier<Response> getTopicWithoutCookie(String loggingRoomId, String topicId) {
    return () -> base
      .get(String.format(PATH_ID + "/topics" + PATH_ID, loggingRoomId, topicId));
  }

  @Step
  public Response getLogMessages(String loggingRoomId, String topicId, Cookie cookie){
    return doByCookiePresent(cookie,
      getLogMessagesWithCookie(loggingRoomId, topicId, cookie),
      getLogMessagesWithoutCookie(loggingRoomId, topicId));
  }

  private Supplier<Response> getLogMessagesWithCookie(String loggingRoomId, String topicId, Cookie cookie) {
    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID + "/topics" + PATH_ID + "/log-messages", loggingRoomId, topicId));
  }

  private Supplier<Response> getLogMessagesWithoutCookie(String loggingRoomId, String topicId) {
    return () -> base
      .get(String.format(PATH_ID + "/topics" + PATH_ID + "/log-messages", loggingRoomId, topicId));
  }

  @Step
  public Response createLogMessage(String loggingRoomId, String topicId, LogMessageWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, createLogMessageWithCookie(loggingRoomId, topicId, request, cookie),
      createLogMessageWithoutCookie(loggingRoomId, topicId, request));
  }

  private Supplier<Response> createLogMessageWithCookie(String loggingRoomId, String topicId, LogMessageWebRequest request, Cookie cookie) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post(String.format(PATH_ID + "/topics" + PATH_ID + "/log-messages", loggingRoomId, topicId));
  }

  private Supplier<Response> createLogMessageWithoutCookie(String loggingRoomId, String topicId, LogMessageWebRequest request) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post(String.format(PATH_ID + "/topics" + PATH_ID + "/log-messages", loggingRoomId, topicId));
  }

  @Step
  public Response createTopic(String loggingRoomId, TopicWebRequest request, Cookie cookie){
    return doByCookiePresent(cookie, 
      createTopicWithCookie(loggingRoomId, request, cookie),
      createTopicWitoutCookie(loggingRoomId, request));
  }

  private Supplier<Response> createTopicWithCookie(String loggingRoomId, TopicWebRequest request, Cookie cookie) {
    return () -> base.cookie(cookie)
      .body(request)
      .contentType(ContentType.JSON)
      .post(String.format(PATH_ID + "/topics", loggingRoomId));
  }

  private Supplier<Response> createTopicWitoutCookie(String loggingRoomId, TopicWebRequest request) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post(String.format(PATH_ID + "/topics", loggingRoomId));
  }

  @Step
  public Response createLoggingRoom(LoggingRoomWebRequest request, Cookie cookie){
    return doByCookiePresent(cookie,
      createLoggingRoomWithCookie(request, cookie),
      createLoggingRoomWitoutCookie( request));
  }

  private Supplier<Response> createLoggingRoomWithCookie(LoggingRoomWebRequest request, Cookie cookie) {
    return () -> base.cookie(cookie)
      .body(request)
      .contentType(ContentType.JSON)
      .post(String.format(PATH_ID));
  }

  private Supplier<Response> createLoggingRoomWitoutCookie(LoggingRoomWebRequest request) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post(String.format(PATH_ID));
  }

  @Step
  public Response updateTopic(String loggingRoomId, String topicId, TopicWebRequest request, Cookie cookie){
    return doByCookiePresent(cookie,
      updateTopicWithCookie(loggingRoomId, topicId, request, cookie),
      updateTopicWitoutCookie(loggingRoomId, topicId, request));
  }

  private Supplier<Response> updateTopicWithCookie(String loggingRoomId, String topicId, TopicWebRequest request, Cookie cookie) {
    return () -> base.cookie(cookie)
      .body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID + "/topics"+ PATH_ID, loggingRoomId, topicId));
  }

  private Supplier<Response> updateTopicWitoutCookie(String loggingRoomId, String topicId, TopicWebRequest request) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID + "/topics" + PATH_ID, loggingRoomId, topicId));
  }

  @Step
  public Response updateLoggingRoom(String loggingRoomId, LoggingRoomWebRequest request, Cookie cookie){
    return doByCookiePresent(cookie,
      updateLoggingRoomWithCookie(loggingRoomId, request, cookie),
      updateLoggingRoomWitoutCookie(loggingRoomId, request));
  }

  private Supplier<Response> updateLoggingRoomWithCookie(String loggingRoomId,  LoggingRoomWebRequest request, Cookie cookie) {
    return () -> base.cookie(cookie)
      .body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID, loggingRoomId));
  }

  private Supplier<Response> updateLoggingRoomWitoutCookie(String loggingRoomId, LoggingRoomWebRequest request) {
    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID, loggingRoomId));
  }

  @Step
  public Response deleteTopic(String loggingRoomId, String topicId, Cookie cookie){
    return doByCookiePresent(cookie,
      deleteTopicWithCookie(loggingRoomId, topicId, cookie),
      deleteTopicWitoutCookie(loggingRoomId, topicId));
  }

  private Supplier<Response> deleteTopicWithCookie(String loggingRoomId, String topicId, Cookie cookie) {
    return () -> base.cookie(cookie)
      .put(String.format(PATH_ID + "/topics"+ PATH_ID, loggingRoomId, topicId));
  }

  private Supplier<Response> deleteTopicWitoutCookie(String loggingRoomId, String topicId) {
    return () -> base
      .put(String.format(PATH_ID + "/topics" + PATH_ID, loggingRoomId, topicId));
  }

  @Step
  public Response deleteLoggingRoom(String loggingRoomId, Cookie cookie){
    return doByCookiePresent(cookie,
      deleteLoggingRoomWithCookie(loggingRoomId, cookie),
      deleteLoggingRoomWitoutCookie(loggingRoomId));
  }

  private Supplier<Response> deleteLoggingRoomWithCookie(String loggingRoomId, Cookie cookie) {
    return () -> base.cookie(cookie)
      .put(String.format(PATH_ID, loggingRoomId));
  }

  private Supplier<Response> deleteLoggingRoomWitoutCookie(String loggingRoomId) {
    return () -> base.put(String.format(PATH_ID, loggingRoomId));
  }
}
