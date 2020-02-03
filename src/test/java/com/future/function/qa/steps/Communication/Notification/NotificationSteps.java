package com.future.function.qa.steps.Communication.Notification;

import com.future.function.qa.api.communication.notification.NotificationAPI;
import com.future.function.qa.data.communication.notification.NotificationData;
import com.future.function.qa.model.request.communication.notification.NotificationWebRequest;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class NotificationSteps extends BaseSteps {

  @Steps
  private NotificationAPI notificationAPI;

  @Autowired
  private NotificationData notificationData;

  @After
  public void cleanup() {
    cleaner.flushAll();
  }

  @Given("^user prepare notification request$")
  public void userPrepareNotificationRequest() {
    notificationAPI.prepare();
  }

  @And("^user hit create notification with description \"([^\"]*)\" and title \"([^\"]*)\"$")
  public void userHitCreateNotificationWithDescriptionAndTitle(String description, String title) throws Throwable {
    NotificationWebRequest request = notificationData.createNotificationRequest(description,
            authData.getResponse().getData().getId(), title);

    Response response = notificationAPI.create(request, authData.getCookie());
    notificationData.setDataResponse(response);

    if (response.getStatusCode() == 200) {
      cleaner.append(DocumentName.NOTIFICATION, notificationData.getDataResponse().getData().getId());
    }
  }

  @Then("^notification response code should be (\\d+)$")
  public void notificationResponseCodeShouldBe(int code) {
    assertThat(notificationData.getResponseCode(), equalTo(code));
  }

  @And("^notification response description should be \"([^\"]*)\"$")
  public void notificationResponseDescriptionShouldBe(String description) throws Throwable {
    assertThat(notificationData.getDataResponse().getData().getDescription(), equalTo(description));
  }

  @And("^notification response title should be \"([^\"]*)\"$")
  public void notificationResponseTitleShouldBe(String title) throws Throwable {
    assertThat(notificationData.getDataResponse().getData().getTitle(), equalTo(title));
  }

  @And("^user hit get notifications$")
  public void userHitGetNotifications() {
    Response response = notificationAPI.getAll(authData.getCookie());
    notificationData.setResponse(response);
  }

  @And("^user hit get total unseen$")
  public void userHitGetTotalUnseen() {
    Response response = notificationAPI.getUnseen(authData.getCookie());
    notificationData.setUnseenResponse(response);
  }

  @And("^total unseen notification should be (\\d+)$")
  public void totalUnseenNotificationShouldBe(int expectedTotal) {
    int total = notificationData.getUnseenResponse().getData().getTotal();
    assertThat(total, equalTo(expectedTotal));
  }

  @When("^user hit read notification$")
  public void userHitReadNotification() {
    String notificationId = notificationData.getDataResponse().getData().getId();
    Response response = notificationAPI.read(notificationId, authData.getCookie());
    notificationData.setResponse(response);
  }
}
