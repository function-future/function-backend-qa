package com.future.function.qa.steps.Scoring.QuestionBank;

import com.future.function.qa.api.question_bank.QuestionBankAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.question_bank.QuestionBankData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import java.util.Collections;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuestionBankSteps extends BaseSteps {

  @Autowired
  private QuestionBankData questionBankData;

  @Steps
  private QuestionBankAPI questionBankAPI;

  @Autowired
  private AuthData authData;

  @Given("^user prepare question bank request$")
  public void userPrepareQuestionBankRequest() {
    questionBankAPI.prepare();
  }


  @And("^user hit create question bank endpoint with title \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void userHitCreateQuestionBankEndpointWithTitleAndDescription(String arg0, String arg1) throws Throwable {
    Response response = questionBankAPI
        .createQuestionBank(questionBankData.createRequest(null, arg0, arg1), authData.getCookie());

    questionBankData.setResponse(response);

  }

  @Then("^question bank response code should be (\\d+)$")
  public void questionBankResponseCodeShouldBe(int arg0) {

    assertEquals(arg0, questionBankData.getResponseCode());
  }

  @And("^question bank error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void questionBankErrorResponseHasKeyAndValue(String arg0, String arg1) throws Throwable {

    assertTrue(questionBankData.getErrorResponse().getErrors().containsKey(arg0));
    assertEquals(Collections.singletonList(arg1), questionBankData.getErrorResponse().getErrors().get(arg0));
  }

  @And("^question bank response data has title \"([^\"]*)\"$")
  public void questionBankResponseDataHasTitle(String arg0) throws Throwable {
    assertEquals(questionBankData.getSingleResponse().getData().getTitle(), arg0);
  }

  @And("^question bank response data has description \"([^\"]*)\"$")
  public void questionBankResponseDataHasDescription(String arg0) throws Throwable {
    assertEquals(questionBankData.getSingleResponse().getData().getDescription(), arg0);
  }

  @And("^question bank response data has id that should not be blank or null$")
  public void questionBankResponseDataHasIdThatShouldNotBeBlankOrNull() {
    assertNotNull(questionBankData.getSingleResponse().getData().getId());
    assertNotEquals(questionBankData.getSingleResponse().getData().getId(), "");

  }
}
