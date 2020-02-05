package com.future.function.qa.steps.Communication.Logging;

import com.future.function.qa.api.communication.logging.LoggingRoomAPI;
import com.future.function.qa.data.communication.logging.LoggingRoomData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.model.request.communication.logging.LogMessageWebRequest;
import com.future.function.qa.model.request.communication.logging.LoggingRoomWebRequest;
import com.future.function.qa.model.request.communication.logging.TopicWebRequest;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.steps.BaseSteps;
import com.future.function.qa.util.DocumentName;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Author: RickyKennedy
 * Created At:10:48 AM 2/4/2020
 */
public class LoggingRoomSteps extends BaseSteps {

  @Steps
  private LoggingRoomAPI loggingRoomAPI;

  @Autowired
  private UserData userData;

  @Autowired
  private LoggingRoomData loggingRoomData;

  @After
  public void cleanup() {
    cleaner.flushAll();
  }

  @Given("^user prepare logging room request$")
  public void userPrepareLoggingRoomRequest() {
    loggingRoomAPI.prepare();
  }

  @When("^user hit create logging room with title \"([^\"]*)\" and description \"([^\"]*)\" and member \"([^\"]*)\"$")
  public void userHitCreateLoggingRoomWithTitleAndDescription(String title, String description, String role){
    List<String> members = userData.getPagingResponse().getData().stream()
      .filter(user -> role.equals(user.getRole()))
      .map(UserWebResponse::getId)
      .collect(Collectors.toList());

    LoggingRoomWebRequest request =
      loggingRoomData.createLoggingRoomRequest(title, description, members);

    Response response = loggingRoomAPI.createLoggingRoom(request, authData.getCookie());
    loggingRoomData.setResponse(response);

    Response response2 = loggingRoomAPI.getLoggingRooms(authData.getCookie());
    loggingRoomData.setOnlyDataLoggingRoomPagingResposne(response2);
    if (response2.getStatusCode() == 200) {
      cleaner.append(DocumentName.LOGGING_ROOM, loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId());
    }
  }

  @When("^user hit get logging rooms$")
  public void userHitGetLoggingRooms(){
    Response response = loggingRoomAPI.getLoggingRooms(authData.getCookie());
    loggingRoomData.setLoggingRoomPagingResponse(response);
  }

  @When("^user hit get logging room detail$")
  public void userHitGetLoggingRoomDetail(){
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    Response response = loggingRoomAPI.getLoggingRoom(loggingRoomId, authData.getCookie());
    loggingRoomData.setLoggingRoomDataResponse(response);
  }

  @Then("^logging room title is \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void loggingRoomTitleAndDescriptionShouldBe(String title, String description) {
    assertThat(loggingRoomData.getLoggingRoomDataResponse().getData().getTitle(), equalTo(title));
    assertThat(loggingRoomData.getLoggingRoomDataResponse().getData().getDescription(), equalTo(description));
  }

  @When("^user hit create topic on logging room with title \"([^\"]*)\"$")
  public void userCreateLoggingRoomTopic(String title){
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    TopicWebRequest request = TopicWebRequest.builder().title(title).build();
    Response response = loggingRoomAPI.createTopic(
      loggingRoomId,
      request,
      authData.getCookie());
    loggingRoomData.setResponse(response);
    Response response2 = loggingRoomAPI.getTopics(loggingRoomId, authData.getCookie());
    loggingRoomData.setOnlyDataTopicPagingResponse(response2);
    if (response2.getStatusCode() == 200) {
      cleaner.append(DocumentName.TOPIC, loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId());
    }
  }

  @When("^user hit get topics$")
  public void userHitGetTopics(){
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    Response response = loggingRoomAPI.getTopics(loggingRoomId, authData.getCookie());
    loggingRoomData.setTopicPagingResponse(response);
  }

  @When("^user hit get topic detail$")
  public void userHitGetTopicDetail(){
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    String topicId = loggingRoomData.getTopicPagingResponse().getData().get(0).getId();
    Response response = loggingRoomAPI.getTopic(loggingRoomId, topicId, authData.getCookie());
    loggingRoomData.setTopicDataResponse(response);
  }

  @Then("^topic title is \"([^\"]*)\"$")
  public void topicTitleShouldBe(String title) {
    assertThat(loggingRoomData.getTopicDataResponse().getData().getTitle(), equalTo(title));
  }

  @When("^user hit create log message with text \"([^\"]*)\"$")
  public void createLogMessages(String text) {
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    String topicId = loggingRoomData.getTopicDataResponse().getData().getId();

    LogMessageWebRequest request =
      LogMessageWebRequest.builder()
        .text(text).build();

    Response response = loggingRoomAPI.createLogMessage(loggingRoomId, topicId, request, authData.getCookie());
    loggingRoomData.setLogMessagesPagingResponse(response);

    Response response2 = loggingRoomAPI.getLogMessages(loggingRoomId, topicId, authData.getCookie());
    loggingRoomData.setOnlyDataLogMessagesPagingResponse(response2);
    if (response2.getStatusCode() == 200) {
      cleaner.append(DocumentName.LOG_MESSAGE, loggingRoomData.getLogMessagePagingResponse().getData().get(0).getId());
    }
  }

  @When("^user hit get log messages$")
  public void userGetLogMessages(){
    String loggingRoomId = loggingRoomData.getLoggingRoomPagingResponse().getData().get(0).getId();
    String topicId = loggingRoomData.getTopicDataResponse().getData().getId();

    Response response = loggingRoomAPI.getLogMessages(loggingRoomId, topicId, authData.getCookie());
    loggingRoomData.setLogMessagesPagingResponse(response);
  }

  @Then("^log message text is \"([^\"]*)\"$")
  public void logMessageShouldBe(String text) {
    assertThat(loggingRoomData.getLogMessagePagingResponse().getData().get(0).getText(), equalTo(text));
  }

  @Then("^logging room response code should be (\\d+)$")
  public void loggingRoomResponseCodeShouldBe(int statusCode) {
    assertThat(loggingRoomData.getResponseCode(), equalTo(statusCode));
  }

  @When("^logging room paging response size equal (\\d+)$")
  public void loggingRoomPagingResponseSizeShouldBe(int size) {
    assertThat(loggingRoomData.getLoggingRoomPagingResponse().getData().size(), equalTo(size));
  }

  @When("^topic paging response size equal (\\d+)$")
  public void topicPagingResponseSizeShouldBe(int size) {
    assertThat(loggingRoomData.getTopicPagingResponse().getData().size(), equalTo(size));
  }

  @When("^log messages paging response size equal (\\d+)$")
  public void logMessagePagingResponseSizeShouldBe(int size) {
    assertThat(loggingRoomData.getLogMessagePagingResponse().getData().size(), equalTo(size));
  }

}
