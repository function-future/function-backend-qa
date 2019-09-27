package com.future.function.qa.steps.Scoring.Room;

import com.future.function.qa.api.scoring.room.RoomAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.data.scoring.assignment.AssignmentData;
import com.future.function.qa.data.scoring.room.RoomData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class RoomSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Autowired
  private RoomData roomData;

  @Autowired
  private AssignmentData assignmentData;

  @Autowired
  private UserData userData;

  @Steps
  private RoomAPI roomAPI;

  @And("^user get first assignment id and store id$")
  public void userGetFirstAssignmentIdAndStoreId() {
    String assignmentId = assignmentData.getPagedResponse().getData().get(0).getId();
    roomData.setAssignmentId(assignmentId);
  }

  @And("^user get first student id and store id$")
  public void userGetFirstStudentIdAndStoreId() {
    String studentId = userData.getPagingResponse().getData().get(0).getId();
    roomData.setStudentId(studentId);
  }

  @Then("^user prepare room request$")
  public void userPrepareRoomRequest() {
    roomAPI.prepare(roomData.getAssignmentId(), roomData.getStudentId());
  }

  @When("^user hit get or create room endpoint$")
  public void userHitGetOrCreateRoomEndpoint() {

    Response response = roomAPI.getOrCreateRoom(authData.getCookie());
    roomData.setResponse(response);
  }

  @Then("^room error response code should be (\\d+)$")
  public void roomErrorResponseCodeShouldBe(int code) {

    assertEquals(roomData.getErrorResponse().getCode(), code);
  }

  @Then("^room response code should be (\\d+)$")
  public void roomResponseCodeShouldBe(int code) {

    assertEquals(roomData.getSingleResponse().getCode(), code);
  }

  @And("^room response body assignment id should be the previous fetched assignment id$")
  public void roomResponseBodyAssignmentIdShouldBeThePreviousFetchedAssignmentId() {

    assertEquals(roomData.getAssignmentId(), roomData.getSingleResponse().getData().getAssignment().getId());
  }

  @And("^room response body student id should be the previous fetched student id;$")
  public void roomResponseBodyStudentIdShouldBeThePreviousFetchedStudentId() {

    assertEquals(roomData.getStudentId(), roomData.getSingleResponse().getData().getStudent().getId());
  }

  @And("^room response body point should be 0$")
  public void roomResponseBodyPointShouldBeZero() {

    assertEquals(0, roomData.getSingleResponse().getData().getPoint().intValue());
  }
}
