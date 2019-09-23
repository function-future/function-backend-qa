package com.future.function.qa.steps.Scoring.Quiz;

import com.future.function.qa.api.scoring.quiz.QuizAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.question_bank.QuestionBankData;
import com.future.function.qa.data.scoring.quiz.QuizData;
import com.future.function.qa.model.request.scoring.quiz.QuizWebRequest;
import com.future.function.qa.model.response.question_bank.QuestionBankWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuizSteps extends BaseSteps {

  @Autowired
  private QuizData quizData;

  @Autowired
  private QuestionBankData questionBankData;

  @Autowired
  private AuthData authData;

  @Steps
  private QuizAPI quizAPI;

  @Then("^quiz error response code should be (\\d+)$")
  public void quizErrorResponseCodeShouldBe(int expectedCode) {
    assertEquals(expectedCode, quizData.getErrorResponse().getCode());
  }

  @When("^user hit create quiz endpoint with title \"([^\"]*)\", description \"([^\"]*)\", trials (\\d+), timeLimit (\\d+),endDate (\\d+), startDate (\\d+), questionCount (\\d+), and question bank ids get from question bank data$")
  public void userHitCreateQuizEndpointWithTitleDescriptionTrialsTimeLimitEndDateStartDateQuestionCountAndQuestionBankIdsGetFromQuestionBankData(
      String title, String description, int trials, int timeLimit, int endDate, int startDate, int questionCount) throws Throwable {

    List<String> questionBanks = questionBankData.getPagedResponse().getData().stream()
        .map(QuestionBankWebResponse::getId)
        .collect(Collectors.toList());
    QuizWebRequest request = quizData.createRequest(title, description, timeLimit, trials, startDate, endDate,
        questionCount, questionBanks);
    Response response = quizAPI.createQuiz(request, authData.getCookie());
    quizData.setResponse(response);
  }

  @And("^quiz error response code body should have key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void quizErrorResponseCodeBodyShouldHaveKeyAndValue(String key, String value) throws Throwable {

    boolean containsKey = quizData.getErrorResponse().getErrors().containsKey(key);
    assertTrue(containsKey);
    String error = quizData.getErrorResponse().getErrors().get(key).get(0);
    assertEquals(value, error);
  }

  @Then("^quiz response code should be (\\d+)$")
  public void quizResponseCodeShouldBe(int expectedCode) {
    assertEquals(expectedCode, quizData.getSingleResponse().getCode());
  }

  @And("^quiz response body should have title \"([^\"]*)\"$")
  public void quizResponseBodyShouldHaveTitle(String expectedTitle) throws Throwable {
    String title = quizData.getSingleResponse().getData().getTitle();
    assertEquals(expectedTitle, title);
  }

  @And("^quiz response body should have description \"([^\"]*)\"$")
  public void quizResponseBodyShouldHaveDescription(String expectedDescription) throws Throwable {
    String description = quizData.getSingleResponse().getData().getDescription();
    assertEquals(expectedDescription, description);
  }

  @And("^quiz response body should have trials (\\d+)$")
  public void quizResponseBodyShouldHaveTrials(int expectedTrials) {
    assertEquals(expectedTrials, quizData.getSingleResponse().getData().getTrials().intValue());
  }

  @And("^quiz response body should have all ids of question bank list$")
  public void quizResponseBodyShouldHaveAllIdsOfQuestionBankList() {
    List<String> questionBanks = questionBankData.getPagedResponse().getData().stream()
        .map(QuestionBankWebResponse::getId)
        .collect(Collectors.toList());

    List<String> questionBankIds = quizData.getSingleResponse().getData().getQuestionBanks().stream()
        .map(QuestionBankWebResponse::getId)
        .collect(Collectors.toList());

    assertEquals(questionBanks, questionBankIds);
  }

  @Then("^user prepare quiz request with batchCode \"([^\"]*)\"$")
  public void userPrepareQuizRequestWithBatchCode(String batchCode) throws Throwable {
    quizAPI.prepare(batchCode);
  }
}
