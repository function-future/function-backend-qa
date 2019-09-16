package com.future.function.qa.steps.Scoring.QuestionBank;

import com.future.function.qa.api.question_bank.QuestionBankAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.question_bank.QuestionBankData;
import com.future.function.qa.model.request.question_bank.QuestionBankWebRequest;
import com.future.function.qa.model.response.question_bank.QuestionBankWebResponse;
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

  @And("^user hit get question bank endpoint with id of previous created data$")
  public void userHitGetQuestionBankEndpointWithIdOfPreviousCreatedData() {
    String id = questionBankData.getSingleResponse().getData().getId();
    Response response = questionBankAPI.getQuestionBank(id, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @And("^user hit get question bank endpoint with id \"([^\"]*)\"$")
  public void userHitGetQuestionBankEndpointWithId(String arg0) throws Throwable {
    Response response = questionBankAPI.getQuestionBank(arg0, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @And("^question bank response status should be \"([^\"]*)\"$")
  public void questionBankResponseStatusShouldBe(String arg0) throws Throwable {
    assertEquals(arg0, questionBankData.getSingleResponse().getStatus());
  }

  @And("^user hit get all question banks endpoint$")
  public void userHitGetAllQuestionBanksEndpoint() {
    Response response = questionBankAPI.getAllQuestionBank(authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank paging response code should be (\\d+)$")
  public void questionBankPagingResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, questionBankData.getPagedResponse().getCode());
  }

  @And("^question bank paging response data should contains title \"([^\"]*)\"$")
  public void questionBankPagingResponseFirstDataTitleShouldBe(String arg0) throws Throwable {
    boolean isAnyMatch = questionBankData.getPagedResponse().getData()
        .stream().map(QuestionBankWebResponse::getTitle)
        .anyMatch(title -> title.matches(arg0));
    assertTrue(isAnyMatch);
  }

  @And("^question bank paging response data should contains description \"([^\"]*)\"$")
  public void questionBankPagingResponseFirstDataDescriptionShouldBe(String arg0) throws Throwable {
    boolean isAnyMatch = questionBankData.getPagedResponse().getData()
        .stream().map(QuestionBankWebResponse::getDescription)
        .anyMatch(description -> description.matches(arg0));
    assertTrue(isAnyMatch);
  }

  @And("^question bank paging response data size should be more than (\\d+)$")
  public void questionBankPagingResponseDataSizeShouldBeMoreThan(int arg0) {
    boolean isSizeZero = questionBankData.getPagedResponse().getData().size() > 0;
    assertTrue(isSizeZero);
  }

  @And("^user hit update question bank endpoint with id of previous created data, title \"([^\"]*)\", and description \"([^\"]*)\"$")
  public void userHitUpdateQuestionBankEndpointWithIdOfPreviousCreatedDataTitleAndDescription(String arg0, String arg1) throws Throwable {
    QuestionBankWebRequest request = questionBankData.createRequest(null, arg0, arg1);
    String id = questionBankData.getSingleResponse().getData().getId();
    Response response = questionBankAPI.updateQuestionBank(id, request, authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank error response code should be (\\d+)$")
  public void questionBankErrorResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, questionBankData.getErrorResponse().getCode());
  }

  @And("^question bank error response status should be \"([^\"]*)\"$")
  public void questionBankErrorResponseStatusShouldBe(String arg0) throws Throwable {
    assertEquals(arg0, questionBankData.getErrorResponse().getStatus());
  }

  @And("^user hit delete question bank endpoint with id of previous created data$")
  public void userHitDeleteQuestionBankEndpointWithIdOfPreviousCreatedData() {
    Response response = questionBankAPI.deleteQuestionBank(questionBankData.getSingleResponse().getData().getId(),
        authData.getCookie());
    questionBankData.setResponse(response);
  }

  @Then("^question bank base response code should be (\\d+)$")
  public void questionBankBaseResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, questionBankData.getBaseResponse().getCode());
  }
}
