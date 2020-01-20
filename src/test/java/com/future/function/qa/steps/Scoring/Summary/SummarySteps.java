package com.future.function.qa.steps.Scoring.Summary;

import com.future.function.qa.api.scoring.summary.SummaryAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.data.scoring.summary.SummaryData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SummarySteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Autowired
  private UserData userData;

  @Autowired
  private SummaryData summaryData;

  @Steps
  private SummaryAPI summaryAPI;


  @And("^user store studentId$")
  public void userStoreStudentId() {
    String studentId = authData.getResponse().getData().getId();
    summaryData.setStudentId(studentId);
  }

  @And("^user prepare summary request$")
  public void userPrepareSummaryRequest() {
    summaryAPI.prepare(summaryData.getStudentId());
  }

  @When("^user hit get summary endpoint with type \"([^\"]*)\"$")
  public void userHitGetSummaryEndpoint(String type) {
    Response response = summaryAPI.getStudentSummary(type, authData.getCookie());
    summaryData.setResponse(response);
  }

  @Then("^summary error response code should be (\\d+)$")
  public void summaryErrorResponseCodeShouldBe(int code) {
    assertEquals(code, summaryData.getErrorResponse().getCode());
  }

  @Then("^summary response code should be (\\d+)$")
  public void summaryResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, summaryData.getSingleResponse().getCode());
  }

  @And("^summary response body studentId should be the same with stored student id$")
  public void summaryResponseBodyStudentIdShouldBeTheSameWithStoredStudentId() {
    assertEquals(summaryData.getStudentId(), summaryData.getSingleResponse().getData().getStudentId());
  }

  @And("^summary response body scores should all match type \"([^\"]*)\"$")
  public void summaryResponseBodyScoresShouldAllMatchTypeWithOr(String type)
      throws Throwable {
    boolean result = summaryData.getSingleResponse().getData()
        .getScores()
        .stream()
        .allMatch(score -> score.getType().equals(type));
    assertTrue(result);
  }
}
