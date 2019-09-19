package com.future.function.qa.steps.Scoring.Question;

import com.future.function.qa.api.scoring.question.QuestionAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.question_bank.QuestionBankData;
import com.future.function.qa.data.scoring.option.OptionData;
import com.future.function.qa.data.scoring.question.QuestionData;
import com.future.function.qa.model.request.scoring.option.OptionWebRequest;
import com.future.function.qa.model.response.scoring.option.OptionWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.List;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QuestionSteps extends BaseSteps {

  @Autowired
  private QuestionBankData questionBankData;

  @Autowired
  private QuestionData questionData;

  @Autowired
  private OptionData optionData;

  @Autowired
  private AuthData authData;

  @Steps
  private QuestionAPI questionAPI;

  @Given("^user prepare question request$")
  public void userPrepareQuestionRequest() {
    questionAPI.prepare(questionData.getQuestionBankId());
  }

  @When("^user hit create question endpoint with label \"([^\"]*)\", options, and id from created question bank$")
  public void userHitCreateQuestionEndpointWithLabelOptionsAndIdFromCreatedQuestionBank(String questionLabel) throws Throwable {

    List<OptionWebRequest> options = optionData.createRequest(null, "Option", false, false, true, false);
    Response response = questionAPI.createQuestion(questionData.createRequest(questionLabel, options), authData.getCookie());
    questionData.setResponse(response);
    optionData.setResponse(questionData.getSingleResponse().getData().getOptions());
  }

  @Then("^question error response code should be (\\d+)$")
  public void questionErrorResponseCodeShouldBe(int expectedCode) {
    assertEquals(expectedCode, questionData.getErrorResponse().getCode());
  }

  @When("^user hit create question endpoint with label \"([^\"]*)\", empty options, and id from created question bank$")
  public void userHitCreateQuestionEndpointWithLabelEmptyOptionsAndIdFromCreatedQuestionBank(String questionLabel) throws Throwable {

    List<OptionWebRequest> options = optionData.createRequest(null, "Option", false, false, true, false);
    options.remove(1);
    Response response = questionAPI.createQuestion(questionData.createRequest(questionLabel, options), authData.getCookie());
    questionData.setResponse(response);
  }

  @And("^question error response body with key \"([^\"]*)\" should be with value \"([^\"]*)\"$")
  public void questionErrorResponseBodyWithKeyShouldBeWithValue(String key, String value) throws Throwable {
    assertTrue(questionData.getErrorResponse().getErrors().containsKey(key));
    assertEquals(questionData.getErrorResponse().getErrors().get(key), Collections.singletonList(value));
  }

  @Then("^question response code should be (\\d+)$")
  public void questionResponseCodeShouldBe(int expectedCode) {
    assertEquals(questionData.getSingleResponse().getCode(), expectedCode);
  }

  @And("^question response body label should be \"([^\"]*)\"$")
  public void questionResponseBodyLabelShouldBe(String label) throws Throwable {

    assertEquals(questionData.getSingleResponse().getData().getLabel(), label);
  }

  @And("^question response body options size should be inputted options size$")
  public void questionResponseBodyOptionsShouldBeInputtedOptions() {

    List<OptionWebResponse> optionResponses = questionData.getSingleResponse().getData().getOptions();
    assertEquals(optionResponses.size(), optionData.getRequestList().size());
  }

  @And("^question response body id should not be null$")
  public void questionResponseBodyIdShouldNotBeNull() {
    assertNotNull(questionData.getSingleResponse().getData().getId());
  }

  @And("^get first question bank data id$")
  public void getFirstQuestionBankDataId() {
    questionData.setQuestionBankId(questionBankData.getPagedResponse().getData().get(0).getId());
  }
}