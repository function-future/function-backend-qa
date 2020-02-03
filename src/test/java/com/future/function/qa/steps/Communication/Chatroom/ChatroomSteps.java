package com.future.function.qa.steps.Communication.Chatroom;

import com.future.function.qa.api.communication.chatroom.ChatroomAPI;
import com.future.function.qa.data.communication.chatroom.ChatroomData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.model.request.communication.chatroom.ChatroomLimitWebRequest;
import com.future.function.qa.model.request.communication.chatroom.ChatroomWebRequest;
import com.future.function.qa.model.request.communication.chatroom.MessageWebRequest;
import com.future.function.qa.model.response.communication.chatroom.ChatroomDetailWebResponse;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.model.response.embedded.ParticipantDetailWebResponse;
import com.future.function.qa.steps.BaseSteps;
import com.future.function.qa.util.DocumentName;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ChatroomSteps extends BaseSteps {

  @Steps
  private ChatroomAPI chatroomAPI;

  @Autowired
  private UserData userData;

  @Autowired
  private ChatroomData chatroomData;

  @After
  public void cleanup() {
    cleaner.flushAll();
  }

  @Given("^user prepare chatroom request$")
  public void userPrepareChatroomRequest() {
    chatroomAPI.prepare();
  }

  @When("^user hit create chatroom with name \"([^\"]*)\"$")
  public void userHitCreateChatroomWithName(String chatroomName) throws Throwable {
    List<String> members = userData.getPagingResponse().getData().stream()
            .map(UserWebResponse::getId)
            .collect(Collectors.toList());
    members.add(authData.getResponse().getData().getId());

    ChatroomWebRequest request = chatroomData.createChatroomRequest(chatroomName, null, members);

    Response response = chatroomAPI.create(request, authData.getCookie());
    chatroomData.setDetailResponse(response);

    if (chatroomData.getResponseCode() == 200) {
      cleaner.append(DocumentName.CHATROOM, chatroomData.getDetailResponse()
        .getData()
        .getId());
    }
  }

  @Then("^chatroom response code should be (\\d+)$")
  public void chatroomResponseCodeShouldBe(int statusCode) {
    assertThat(chatroomData.getResponseCode(), equalTo(statusCode));
  }

  @When("^user hit get chatroom list endpoint$")
  public void userHitGetChatroomListEndpoint() {
    Response response = chatroomAPI.getChatrooms(authData.getCookie());
    chatroomData.setResponse(response);
  }

  @When("^user hit set chatroom limit endpoint with limit equals (\\d+)$")
  public void userHitSetChatroomLimitEndpointWithLimitEquals(int limit) {
    ChatroomLimitWebRequest request = chatroomData.createChatroomLimitRequest(limit);

    Response response = chatroomAPI.setChatroomsLimit(authData.getCookie(), request);
    chatroomData.setResponse(response);
  }

  @When("^user hit unset chatroom limit endpoint$")
  public void userHitUnsetChatroomLimitEndpoint() {
    Response response = chatroomAPI.unsetChatroomsLimit(authData.getCookie());
    chatroomData.setResponse(response);
  }

  @When("^user hit get chatroom detail$")
  public void userHitGetChatroomDetail() {
    String chatroomId = chatroomData.getDetailResponse().getData().getId();

    Response response = chatroomAPI.getChatroomDetail(chatroomId, authData.getCookie());
    chatroomData.setDetailResponse(response);
  }

  @Then("^chatroom has member with name \"([^\"]*)\"$")
  public void chatroomHasMemberWithEmail(String name) throws Throwable {
    long totalMatch = chatroomData.getDetailResponse().getData().getMembers().stream()
            .filter(participant -> participant.getName().equals(name))
            .count();
    assertThat((int) totalMatch, greaterThan(0));
  }

  @Then("^chatroom has name \"([^\"]*)\"$")
  public void chatroomHasName(String chatroomName) throws Throwable {
    assertThat(chatroomData.getDetailResponse().getData().getName(), equalTo(chatroomName));
  }

  @And("^user hit update chatroom with name \"([^\"]*)\", picture \"([^\"]*)\"$")
  public void userHitUpdateChatroomWithNamePictureNull(String name, String pictureUrl) throws Throwable {
    ChatroomDetailWebResponse chatroomDetail = chatroomData.getDetailResponse().getData();
    List<String> members = chatroomDetail.getMembers()
            .stream()
            .map(ParticipantDetailWebResponse::getId)
            .collect(Collectors.toList());
    ChatroomWebRequest request = chatroomData.createChatroomRequest(name, pictureUrl.isEmpty() ? null : pictureUrl, members);

    Response response = chatroomAPI.updateChatroom(chatroomDetail.getId(), request, authData.getCookie());
    chatroomData.setDetailResponse(response);
  }

  @When("^user hit enter chatroom endpoint$")
  public void userHitEnterChatroomEndpoint() {
    Response response = chatroomAPI.enterChatroom(chatroomData.getDetailResponse().getData().getId(), authData.getCookie());
    chatroomData.setResponse(response);
  }

  @When("^user hit leave chatroom endpoint$")
  public void userHitLeaveChatroomEndpoint() {
    Response response = chatroomAPI.leaveChatroom(chatroomData.getDetailResponse().getData().getId(), authData.getCookie());
    chatroomData.setResponse(response);
  }

  @And("^user hit create message with content \"([^\"]*)\"$")
  public void userHitCreateMessageWithContent(String message) throws Throwable {
    MessageWebRequest request = chatroomData.createMessageRequest(message);
    String chatroomId = chatroomData.getDetailResponse().getData().getId();

    Response response = chatroomAPI.createMessages(chatroomId, request, authData.getCookie());
    if (response.getStatusCode() == 201) {
      chatroomData.setMessagePagingResponse(chatroomAPI.getMessages(chatroomId, authData.getCookie()));
      String lastMessageId = chatroomData.getMessagePagingResponse().getData().get(0).getId();
      cleaner.append(DocumentName.MESSAGE, lastMessageId);
      cleaner.append(DocumentName.MESSAGE_STATUS, lastMessageId);
    }
    chatroomData.setResponse(response);
  }

  @When("^user hit get messages$")
  public void userHitGetMessages() {
    Response response = chatroomAPI.getMessages(chatroomData.getDetailResponse().getData().getId(), authData.getCookie());
    chatroomData.setMessagePagingResponse(response);
  }

  @Then("^last message should be \"([^\"]*)\"$")
  public void lastMessageShouldBe(String message) {
    String lastMessage = chatroomData.getMessagePagingResponse().getData().get(0).getText();
    assertThat(lastMessage, equalTo(message));
  }

  @When("^user hit get messages after pivot$")
  public void userHitGetMessagesAfterPivot() {
    String pivotMessageId = chatroomData.getMessagePagingResponse().getData().get(1).getId();

    Response response = chatroomAPI.getMessagesAfterPivot(chatroomData.getDetailResponse().getData().getId(),
            pivotMessageId, authData.getCookie());
    chatroomData.setMessagePagingResponse(response);
  }

  @When("^user hit get messages before pivot$")
  public void userHitGetMessagesBeforePivot() {
    String pivotMessageId = chatroomData.getMessagePagingResponse().getData().get(0).getId();

    Response response = chatroomAPI.getMessagesBeforePivot(chatroomData.getDetailResponse().getData().getId(),
            pivotMessageId, authData.getCookie());
    chatroomData.setMessagePagingResponse(response);
  }

  @When("^user hit update message status$")
  public void userHitUpdateMessageStatus() {
    String pivotMessageId = chatroomData.getMessagePagingResponse().getData().get(0).getId();
    String chatroomId = chatroomData.getDetailResponse().getData().getId();

    Response response = chatroomAPI.updateMessageStatus(chatroomId, pivotMessageId, authData.getCookie());
    chatroomData.setResponse(response);
  }
}
