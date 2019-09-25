package com.future.function.qa.steps.Scoring.Assignment;

import com.future.function.qa.api.scoring.assignment.AssignmentAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.assignment.AssignmentData;
import com.future.function.qa.model.request.scoring.assignment.AssignmentWebRequest;
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


  @And("^user prepare assignment request with batchCode \"([^\"]*)\"$")
  public void userPrepareAssignmentRequestWithBatchCode(String batchCode) throws Throwable {
    assignmentAPI.prepare(batchCode);
  }

  @When("^user hit create assignment endpoint with title \"([^\"]*)\", description \"([^\"]*)\", deadline (\\d+), and empty list of files$")
  public void userHitCreateAssignmentEndpointWithTitleDescriptionDeadlineLAndEmptyListOfFiles(String title, String description, int deadline) throws Throwable {
    AssignmentWebRequest request = assignmentData.createRequest(title, description, (long) deadline, Collections.emptyList());
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
    assertEquals(value, assignmentData.getErrorResponse().getErrors().get(key).get(0));
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
  public void assignmentResponseBodyDeadlineShouldBe(int deadline) {
    assertEquals(assignmentData.getSingleResponse().getData().getDeadline(), (long) deadline);
  }
}
