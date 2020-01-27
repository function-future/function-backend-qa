package com.future.function.qa.steps.Scoring.QuestionBank;

import com.future.function.qa.api.scoring.question_bank.QuestionBankAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.question_bank.QuestionBankData;
import com.future.function.qa.model.request.scoring.question_bank.QuestionBankWebRequest;
import com.future.function.qa.model.response.question_bank.QuestionBankWebResponse;
import com.future.function.qa.steps.BaseSteps;
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
  public void userHitCreateQuestionBankEndpointWithTitleAndDescription(String title, String description) throws Throwable {
    Response response = questionBankAPI
        .createQuestionBank(questionBankData.createRequest(null, title, description), authData.getCookie());

    questionBankData.setResponse(response);

  }

  @Then("^question bank response code should be (\\d+)$")
  public void questionBankResponseCodeShouldBe(int responseCode) {

    assertEquals(responseCode, questionBankData.getResponseCode());
  }

  @And("^question bank error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void questionBankErrorResponseHasKeyAndValue(String key, String value) throws Throwable {

    assertTrue(questionBankData.getErrorResponse().getErrors().containsKey(key));
    assertEquals(Collections.singletonList(value), questionBankData.getErrorResponse().getErrors().get(key));
  }

  @And("^question bank response data has title \"([^\"]*)\"$")
  public void questionBankResponseDataHasTitle(String title) throws Throwable {
    assertEquals(questionBankData.getSingleResponse().getData().getTitle(), title);
  }

  @And("^question bank response data has description \"([^\"]*)\"$")
  public void questionBankResponseDataHasDescription(String description) throws Throwable {
    assertEquals(questionBankData.getSingleResponse().getData().getDescription(), description);
  }

  @And("^question bank response data has id that should not be blank or null$")
  public void questionBankResponseDataHasIdThatShouldNotBeBlankOrNull() {
    assertNotNull(questionBankData.getSingleResponse().getData().getId());
    assertNotEquals(questionBankData.getSingleResponse().getData().getId(), "");

  }

  @And("^user hit get question bank endpoint with id of previous created data$")
  public void userHitGetQuestionBankEndpointWithIdOfPreviousCreatedData() {
    String id = questionBankData.getSingleResponse().getData().getId();
    Response response = questionBankAPI.getQuestionBank(id, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @And("^user hit get question bank endpoint with id \"([^\"]*)\"$")
  public void userHitGetQuestionBankEndpointWithId(String id) throws Throwable {
    Response response = questionBankAPI.getQuestionBank(id, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @And("^question bank response status should be \"([^\"]*)\"$")
  public void questionBankResponseStatusShouldBe(String responseStatus) throws Throwable {
    assertEquals(responseStatus, questionBankData.getSingleResponse().getStatus());
  }

  @And("^user hit get all question banks endpoint$")
  public void userHitGetAllQuestionBanksEndpoint() {
    Response response = questionBankAPI.getAllQuestionBank(authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank paging response code should be (\\d+)$")
  public void questionBankPagingResponseCodeShouldBe(int responseCode) {
    assertEquals(responseCode, questionBankData.getPagedResponse().getCode());
  }

  @And("^question bank paging response data should contains title \"([^\"]*)\"$")
  public void questionBankPagingResponseFirstDataTitleShouldBe(String expectedTitle) throws Throwable {
    boolean isAnyMatch = questionBankData.getPagedResponse().getData()
        .stream().map(QuestionBankWebResponse::getTitle)
        .anyMatch(title -> title.matches(title));
    assertTrue(isAnyMatch);
  }

  @And("^question bank paging response data should contains description \"([^\"]*)\"$")
  public void questionBankPagingResponseFirstDataDescriptionShouldBe(String expectedDescription) throws Throwable {
    boolean isAnyMatch = questionBankData.getPagedResponse().getData()
        .stream().map(QuestionBankWebResponse::getDescription)
        .anyMatch(description -> description.matches(description));
    assertTrue(isAnyMatch);
  }

  @And("^question bank paging response data size should be more than (\\d+)$")
  public void questionBankPagingResponseDataSizeShouldBeMoreThan(int limit) {
    boolean isSizeZero = questionBankData.getPagedResponse().getData().size() > 0;
    assertTrue(isSizeZero);
  }

  @And("^user hit update question bank endpoint with id of previous created data, title \"([^\"]*)\", and description \"([^\"]*)\"$")
  public void userHitUpdateQuestionBankEndpointWithIdOfPreviousCreatedDataTitleAndDescription(String title, String description) throws Throwable {
    QuestionBankWebRequest request = questionBankData.createRequest(null, title, description);
    String id = questionBankData.getSingleResponse().getData().getId();
    Response response = questionBankAPI.updateQuestionBank(id, request, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank error response code should be (\\d+)$")
  public void questionBankErrorResponseCodeShouldBe(int responseCode) {
    assertEquals(responseCode, questionBankData.getErrorResponse().getCode());
  }

  @And("^question bank error response status should be \"([^\"]*)\"$")
  public void questionBankErrorResponseStatusShouldBe(String responseStatus) throws Throwable {
    assertEquals(responseStatus, questionBankData.getErrorResponse().getStatus());
  }

  @And("^user hit delete question bank endpoint with id of previous created data$")
  public void userHitDeleteQuestionBankEndpointWithIdOfPreviousCreatedData() {
    Response response = questionBankAPI.deleteQuestionBank(questionBankData.getSingleResponse().getData().getId(),
        authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank base response code should be (\\d+)$")
  public void questionBankBaseResponseCodeShouldBe(int responseCode) {
    assertEquals(responseCode, questionBankData.getBaseResponse().getCode());
  }
}
