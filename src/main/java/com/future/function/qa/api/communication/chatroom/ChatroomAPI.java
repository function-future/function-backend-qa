package com.future.function.qa.api.communication.chatroom;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.communication.chatroom.ChatroomLimitWebRequest;
import com.future.function.qa.model.request.communication.chatroom.ChatroomWebRequest;
import com.future.function.qa.model.request.communication.chatroom.MessageWebRequest;
import com.future.function.qa.util.Path;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

public class ChatroomAPI extends BaseAPI {

  @Override
  @Step
  public RequestSpecification prepare() {
    base = super.prepare()
            .basePath(Path.CHATROOMS);
    return base;
  }

  @Step
  public Response create(ChatroomWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, createChatroomWithCookie(request, cookie),
            createChatroomWithoutCookie(request));
  }

  private Supplier<Response> createChatroomWithCookie(ChatroomWebRequest request, Cookie cookie) {
    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createChatroomWithoutCookie(ChatroomWebRequest request) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .post();
  }

  @Step
  public Response getChatrooms(Cookie cookie) {
    return doByCookiePresent(cookie, () -> base.cookie(cookie).get(), () -> base.get());
  }

  @Step
  public Response setChatroomsLimit(Cookie cookie, ChatroomLimitWebRequest request) {
    return doByCookiePresent(cookie, setChatroomsLimitWithCookie(request, cookie), setChatroomsLimitWithoutCookie(request));
  }

  private Supplier<Response> setChatroomsLimitWithCookie(ChatroomLimitWebRequest request, Cookie cookie) {
    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post("/_setlimit");
  }

  private Supplier<Response> setChatroomsLimitWithoutCookie(ChatroomLimitWebRequest request) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .post("/_setlimit");
  }

  @Step
  public Response unsetChatroomsLimit(Cookie cookie) {
    return doByCookiePresent(cookie, unsetChatroomsLimitWithCookie(cookie), unsetChatroomsLimitWithoutCookie());
  }

  private Supplier<Response> unsetChatroomsLimitWithCookie(Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .post("/_unsetlimit");
  }

  private Supplier<Response> unsetChatroomsLimitWithoutCookie() {
    return () -> base
            .post("/_unsetlimit");
  }

  @Step
  public Response getChatroomDetail(String id, Cookie cookie) {
    return doByCookiePresent(cookie, getChatroomDetailWithCookie(id, cookie), getChatroomDetailWithoutCookie(id));
  }

  private Supplier<Response> getChatroomDetailWithCookie(String id, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getChatroomDetailWithoutCookie(String id) {
    return () -> base
            .get(String.format(PATH_ID, id));
  }

  @Step
  public Response updateChatroom(String id, ChatroomWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, updateChatroomDetailWithCookie(id, request, cookie),
            updateChatroomDetailWithoutCookie(id, request));
  }

  private Supplier<Response> updateChatroomDetailWithCookie(String id, ChatroomWebRequest request, Cookie cookie) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .cookie(cookie)
            .put(String.format(PATH_ID, id));
  }

  private Supplier<Response> updateChatroomDetailWithoutCookie(String id, ChatroomWebRequest request) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .put(String.format(PATH_ID, id));
  }

  @Step
  public Response enterChatroom(String id, Cookie cookie) {
    return doByCookiePresent(cookie, enterChatroomWithCookie(id, cookie), enterChatroomWithoutCookie(id));
  }

  private Supplier<Response> enterChatroomWithCookie(String id, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .post(String.format(PATH_ID + "/_enter", id));
  }

  private Supplier<Response> enterChatroomWithoutCookie(String id) {
    return () -> base
            .post(String.format(PATH_ID + "/_enter", id));
  }

  @Step
  public Response leaveChatroom(String id, Cookie cookie) {
    return doByCookiePresent(cookie, leaveChatroomWithCookie(id, cookie), leaveChatroomWithoutCookie(id));
  }

  private Supplier<Response> leaveChatroomWithCookie(String id, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .post(String.format(PATH_ID + "/_leave", id));
  }

  private Supplier<Response> leaveChatroomWithoutCookie(String id) {
    return () -> base
            .post(String.format(PATH_ID + "/_leave", id));
  }

  @Step
  public Response getMessages(String id, Cookie cookie) {
    return doByCookiePresent(cookie, getMessagesWithCookie(id, cookie), getMessagesWithoutCookie(id));
  }

  private Supplier<Response> getMessagesWithCookie(String id, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .get(String.format(PATH_ID + "/messages", id));
  }

  private Supplier<Response> getMessagesWithoutCookie(String id) {
    return () -> base
            .get(String.format(PATH_ID + "/messages", id));
  }

  @Step
  public Response createMessages(String id, MessageWebRequest request, Cookie cookie) {
    return doByCookiePresent(cookie, createMessageWithCookie(id, request, cookie),
            createMessageWithoutCookie(id, request));
  }

  private Supplier<Response> createMessageWithCookie(String id, MessageWebRequest request, Cookie cookie) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .cookie(cookie)
            .post(String.format(PATH_ID + "/messages", id));
  }

  private Supplier<Response> createMessageWithoutCookie(String id, MessageWebRequest request) {
    return () -> base.body(request)
            .contentType(ContentType.JSON)
            .post(String.format(PATH_ID + "/messages", id));
  }

  @Step
  public Response getMessagesBeforePivot(String chatroomId, String messageId, Cookie cookie) {
    return doByCookiePresent(cookie, getMessagesBeforePivotWithCookie(chatroomId, messageId, cookie),
            getMessagesBeforePivotWithoutCookie(chatroomId, messageId));
  }

  private Supplier<Response> getMessagesBeforePivotWithCookie(String chatroomId, String messageId, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .queryParam("messageId", messageId)
            .get(String.format(PATH_ID + "/messages", chatroomId));
  }

  private Supplier<Response> getMessagesBeforePivotWithoutCookie(String chatroomId, String messageId) {
    return () -> base
            .queryParam("messageId", messageId)
            .get(String.format(PATH_ID + "/messages", chatroomId));
  }

  @Step
  public Response updateMessageStatus(String chatroomId, String messageId, Cookie cookie) {
    return doByCookiePresent(cookie, updateMessageStatusWithCookie(chatroomId, messageId, cookie),
            updateMessageStatusWithoutCookie(chatroomId, messageId));
  }

  private Supplier<Response> updateMessageStatusWithCookie(String chatroomId, String messageId, Cookie cookie) {
    return () -> base
            .cookie(cookie)
            .put(String.format(PATH_ID + "/messages/%s/_read", chatroomId, messageId));
  }

  private Supplier<Response> updateMessageStatusWithoutCookie(String chatroomId, String messageId) {
    return () -> base
            .put(String.format(PATH_ID + "/messages/%s/_read", chatroomId, messageId));
  }

}
