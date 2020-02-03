package com.future.function.qa.steps.Scoring.Assignment;

import com.future.function.qa.api.scoring.assignment.AssignmentAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.data.scoring.assignment.AssignmentData;
import com.future.function.qa.model.request.scoring.assignment.AssignmentWebRequest;
import com.future.function.qa.model.request.scoring.assignment.CopyAssignmentWebRequest;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.Collections;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentSteps extends BaseSteps {

  @Autowired
  private AssignmentData assignmentData;

  @Autowired
  private AuthData authData;

  @Steps
  private AssignmentAPI assignmentAPI;

  @Autowired
  private ResourceData resourceData;


  @And("^user prepare assignment request with batchCode \"([^\"]*)\"$")
  public void userPrepareAssignmentRequestWithBatchCode(String batchCode) throws Throwable {
    assignmentAPI.prepare(batchCode);
  }

  @When("^user hit create assignment endpoint with title \"([^\"]*)\", description \"([^\"]*)\", deadline (\\d+), and empty list of files$")
  public void userHitCreateAssignmentEndpointWithTitleDescriptionDeadlineLAndEmptyListOfFiles(String title, String description, long deadline) throws Throwable {
    AssignmentWebRequest request = assignmentData.createRequest(title, description, deadline, null);
    Response response = assignmentAPI.createAssignment(request, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @Then("^assignment error response code should be (\\d+)$")
  public void assignmentErrorResponseCodeShouldBe(int code) {
    assertEquals(code, assignmentData.getErrorResponse().getCode());
  }

  @And("^assignment error response body should have key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void assignmentErrorResponseBodyShouldHaveKeyAndValue(String key, String value) throws Throwable {
    assertTrue(assignmentData.getErrorResponse().getErrors().containsKey(key));
    assertTrue(assignmentData.getErrorResponse().getErrors().get(key).stream()
        .anyMatch(errorValue -> errorValue.equals(value)));
  }

  @Then("^assignment response code should be (\\d+)$")
  public void assignmentResponseCodeShouldBe(int code) {
    assertEquals(assignmentData.getSingleResponse().getCode(), code);
  }

  @And("^assignment response body title should be \"([^\"]*)\"$")
  public void assignmentResponseBodyTitleShouldBe(String title) throws Throwable {
    assertEquals(assignmentData.getSingleResponse().getData().getTitle(), title);
  }

  @And("^assignment response body description should be \"([^\"]*)\"$")
  public void assignmentResponseBodyDescriptionShouldBe(String description) throws Throwable {
    assertEquals(assignmentData.getSingleResponse().getData().getDescription(), description);
  }

  @And("^assignment response body deadline should be (\\d+)$")
  public void assignmentResponseBodyDeadlineShouldBe(long deadline) {
    assertEquals(assignmentData.getSingleResponse().getData().getDeadline(), deadline);
  }

  @When("^user hit get all assignment endpoint$")
  public void userHitGetAllAssignmentEndpoint() {
    Response response = assignmentAPI.getAllAssignment(authData.getCookie());
    assignmentData.setResponse(response);
  }

  @Then("^assignment paging response code should be (\\d+)$")
  public void assignmentPagingResponseCodeShouldBe(int code) {
    assertEquals(assignmentData.getPagedResponse().getCode(), code);
  }

  @And("^assignment paging response body should contains title \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void assignmentPagingResponseBodyShouldContainsTitleAndDescription(String title, String description) throws Throwable {
    boolean result = assignmentData.getPagedResponse().getData().stream()
        .anyMatch(assignment -> assignment.getTitle().equals(title) && assignment.getDescription().equals(description));
    assertTrue(result);
  }

  @When("^user hit get assignment endpoint with previous created id$")
  public void userHitGetAssignmentEndpointWithPreviousCreatedId() {
    String id = assignmentData.getSingleResponse().getData().getId();
    Response response = assignmentAPI.getAssignment(id, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user get assignment endpoint with random id$")
  public void userGetAssignmentEndpointWithRandomId() {
    Response response = assignmentAPI.getAssignment("random-id", authData.getCookie());
    assignmentData.setResponse(response);
  }

  @And("^assignment error response status should be \"([^\"]*)\"$")
  public void assignmentErrorResponseStatusShouldBe(String status) throws Throwable {
    assertEquals(assignmentData.getErrorResponse().getStatus(), status);
  }

  @When("^user hit update assignment endpoint with previous get id, title \"([^\"]*)\", description \"([^\"]*)\", deadline (\\d+), and empty list of files$")
  public void userHitUpdateAssignmentEndpointWithPreviousGetIdTitleDescriptionDeadlineAndEmptyListOfFiles(String title, String description, long deadline) throws Throwable {
    AssignmentWebRequest request = assignmentData.createRequest(title, description, deadline, null);
    String id = assignmentData.getSingleResponse().getData().getId();
    Response response = assignmentAPI.updateAssignment(id, request, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user hit delete assignment endpoint with previous get id$")
  public void userHitDeleteAssignmentEndpointWithPreviousGetId() {
    String id = assignmentData.getSingleResponse().getData().getId();
    Response response = assignmentAPI.deleteAssignment(id, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user hit delete assignment endpoint with random id$")
  public void userHitDeleteAssignmentEndpointWithRandomId() {
    Response response = assignmentAPI.deleteAssignment("random-id", authData.getCookie());
    assignmentData.setResponse(response);
  }

  @Then("^assignment base response code should be (\\d+)$")
  public void assignmentBaseResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, assignmentData.getBaseResponse().getCode());
  }

  @When("^user hit copy assignment with batchCode \"([^\"]*)\" and assignment id of previous get id$")
  public void userHitCopyAssignmentWithBatchCodeAndQuizIdOfPreviousGetId(String batchCode) throws Throwable {
    CopyAssignmentWebRequest request = assignmentData.createCopyRequest(assignmentData.getSingleResponse().getData().getId(), batchCode);
    Response response = assignmentAPI.copyAssignment(request, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user hit copy assignment with batchCode\"([^\"]*)\" and random assignment id$")
  public void userHitCopyAssignmentWithBatchCodeAndRandomAssignmentId(String batchCode) throws Throwable {
    CopyAssignmentWebRequest request = assignmentData.createCopyRequest("random-id", batchCode);
    Response response = assignmentAPI.copyAssignment(request, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user hit create assignment endpoint with title \"([^\"]*)\", description \"([^\"]*)\", deadline (\\d+), and uploaded file$")
  public void userHitCreateAssignmentEndpointWithTitleDescriptionDeadlineAndUploadedFile(String title,
      String description, long deadline) throws Throwable {
    String fileId = resourceData.getCreatedResponse().getData().getId();
    AssignmentWebRequest request = assignmentData.createRequest(title, description, deadline,
        Collections.singletonList(fileId));
    Response response = assignmentAPI.createAssignment(request, authData.getCookie());
    assignmentData.setResponse(response);
  }

  @When("^user hit create assignment endpoint with title \"([^\"]*)\", description \"([^\"]*)\", deadline (\\d+), and non-exist file$")
  public void userHitCreateAssignmentEndpointWithTitleDescriptionDeadlineAndNonExistFile(String title,
      String description, long deadline) throws Throwable {
    String fileId = "abc";
    AssignmentWebRequest request = assignmentData.createRequest(title, description, deadline,
        Collections.singletonList(fileId));
    Response response = assignmentAPI.createAssignment(request, authData.getCookie());
    assignmentData.setResponse(response);
  }
}
