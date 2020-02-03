package com.future.function.qa.steps.Communication.Reminder;

import com.future.function.qa.api.communication.reminder.ReminderAPI;
import com.future.function.qa.data.communication.reminder.ReminderData;
import com.future.function.qa.model.request.communication.reminder.ReminderWebRequest;
import com.future.function.qa.model.response.communication.reminder.ReminderDetailResponse;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReminderSteps extends BaseSteps {

  @Steps
  private ReminderAPI reminderAPI;

  @Autowired
  private ReminderData reminderData;

  @After
  public void cleanup() {
    cleaner.flushAll();
  }

  @Given("^user prepare reminder request$")
  public void userPrepareReminderRequest() {
    reminderAPI.prepare();
  }

  @And("^user hit create reminder endpoint with description \"([^\"]*)\", hour (\\d+), minute (\\d+), title \"([^\"]*)\", monthlyDate (\\d+)$")
  public void createReminder(String description, int hour, int minute, String title, int date) throws Throwable {
    List<String> members = Collections.singletonList(authData.getResponse().getData().getId());
    List<String> repeatDays = Collections.emptyList();
    ReminderWebRequest request = reminderData.createReminderRequest(title, description, true,
            date, repeatDays, hour, minute, members);

    Response response = reminderAPI.create(request, authData.getCookie());
    reminderData.setDetailResponse(response);
    if (response.getStatusCode() == 200) {
      cleaner.append(DocumentName.REMINDER, reminderData.getDetailResponse().getData().getId());
    }
  }

  @Then("^reminder response code should be (\\d+)$")
  public void reminderResponseCodeShouldBe(int code) {
    assertThat(reminderData.getResponseCode(), equalTo(code));
  }

  @And("^reminder response description should be \"([^\"]*)\"$")
  public void reminderResponseDescriptionShouldBe(String description) {
    assertThat(reminderData.getDetailResponse().getData().getDescription(), equalTo(description));
  }

  @And("^reminder response title should be \"([^\"]*)\"$")
  public void reminderResponseTitleShouldBe(String title) throws Throwable {
    assertThat(reminderData.getDetailResponse().getData().getTitle(), equalTo(title));
  }

  @And("^user hit get reminders$")
  public void userHitGetReminders() {
    Response response = reminderAPI.getAll(authData.getCookie());
    reminderData.setResponse(response);
  }

  @And("^reminder response first data description should be \"([^\"]*)\"$")
  public void reminderResponseFirstDataDescriptionShouldBe(String description) throws Throwable {
    assertThat(reminderData.getPagingResponse().getData().get(0).getDescription(), equalTo(description));
  }

  @And("^reminder response first data title should be \"([^\"]*)\"$")
  public void reminderResponseFirstDataTitleShouldBe(String title) throws Throwable {
    assertThat(reminderData.getPagingResponse().getData().get(0).getTitle(), equalTo(title));
  }

  @And("^user hit get reminder detail$")
  public void userHitGetReminderDetail() {
    String reminderId = reminderData.getDetailResponse().getData().getId();
    Response response = reminderAPI.getDetail(reminderId, authData.getCookie());
    reminderData.setDetailResponse(response);
  }

  @When("^user hit update reminder with title \"([^\"]*)\", description \"([^\"]*)\", hour (\\d+), minute (\\d+)$")
  public void userHitUpdateReminderWithTitleAndDescription(String title, String description, int hour, int minute) throws Throwable {
    ReminderDetailResponse reminder = reminderData.getDetailResponse().getData();
    List<String> members = reminder.getMembers()
            .stream()
            .map(ParticipantDetailWebResponse::getId)
            .collect(Collectors.toList());
    ReminderWebRequest request = reminderData.createReminderRequest(title, description, reminder.getIsRepeatedMonthly(),
            reminder.getMonthlyDate(), reminder.getRepeatDays(), hour, minute, members);

    Response response = reminderAPI.updateReminder(reminder.getId(), request, authData.getCookie());
    reminderData.setDetailResponse(response);
  }

  @And("^user hit delete reminder$")
  public void userHitDeleteReminder() {
    Response response = reminderAPI.delete(reminderData.getDetailResponse().getData().getId(), authData.getCookie());
    reminderData.setResponse(response);
  }
}
