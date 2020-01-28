package com.future.function.qa.steps.Scoring.Student_Question;

import com.future.function.qa.api.scoring.student_question.StudentQuestionAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.quiz.QuizData;
import com.future.function.qa.data.scoring.student_question.StudentQuestionData;
import com.future.function.qa.data.scoring.student_quiz.StudentQuizData;
import com.future.function.qa.model.request.scoring.student_question.StudentQuestionWebRequest;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class StudentQuestionSteps extends BaseSteps {

  @Autowired
  private QuizData quizData;

  @Autowired
  private AuthData authData;

  @Autowired
  private StudentQuestionData studentQuestionData;

  @Autowired
  private StudentQuizData studentQuizData;

  @Steps
  private StudentQuestionAPI studentQuestionAPI;

  @And("^user get first quiz id and store id$")
  public void userGetFirstQuizIdAndStoreId() {
    String quizId = quizData.getPagedResponse().getData().get(0).getId();
    studentQuestionData.setQuizId(quizId);
  }

  @And("^user prepare student question request with batchCode \"([^\"]*)\"$")
  public void userPrepareStudentQuestionRequestWithBatchCode(String batchCode) throws Throwable {

    studentQuestionData.setBatchCode(batchCode);
    studentQuestionAPI.prepare(studentQuestionData.getBatchCode(), studentQuestionData.getQuizId());
  }

  @When("^user hit find all unanswered questions$")
  public void userHitFindAllUnansweredQuestions() {
    Response response = studentQuestionAPI.findAllUnansweredQuestion(authData.getCookie());
    studentQuestionData.setResponse(response);
  }

  @Then("^student question error response code should be (\\d+)$")
  public void studentQuestionErrorResponseCodeShouldBe(int code) {
    assertEquals(code, studentQuestionData.getErrorResponse().getCode());
  }

  @Then("^student question response code should be (\\d+)$")
  public void studentQuestionResponseCodeShouldBe(int code) {
    assertEquals(code, studentQuestionData.getPagedResponse().getCode());
  }

  @And("^student question size should be more than (\\d+)$")
  public void studentQuestionSizeShouldBeMoreThan(int size) {
    assertTrue(studentQuestionData.getPagedResponse().getData().size() > size);
  }

  @And("^user store the current trials from student quiz$")
  public void userStoreTheCurrentTrialsFromStudentQuiz() {
    studentQuestionData.setCurrentTrials(studentQuizData.getSingleResponse().getData().getTrials());
  }

  @And("^user hit post answers endpoint with unanswered questions as \"([^\"]*)\"$")
  public void userHitPostAnswersEndpointWithUnansweredQuestions(String loginStatus) {
    Map<Integer, String> answers = null;
    if(loginStatus.equals("user")) {
      answers = generateRandomAnswers();
    }
    Response response = studentQuestionAPI
        .postAnswers(studentQuestionData.createRequestList(answers), authData.getCookie());
    studentQuestionData.setResponse(response);
  }

  private Map<Integer, String> generateRandomAnswers() {
    Map<Integer, String> answers = new HashMap<>();
    studentQuestionData.getPagedResponse().getData()
        .forEach(questions -> {
          String optionId = questions.getOptions().get(ThreadLocalRandom.current().nextInt(0, 3 + 1)).getId();
          answers.put(questions.getNumber(), optionId);
        });
    return answers;
  }

  @And("^student question response body trials should not be null$")
  public void studentQuestionResponseBodyTrialsShouldNotBeNull() {
    assertNotNull( studentQuestionData.getQuizDetailResponse().getData().getTrials());
  }

  @Then("^student quiz detail response code should be (\\d+)$")
  public void studentQuizDetailResponseCodeShouldBe(int code) {
    assertEquals(studentQuestionData.getQuizDetailResponse().getCode(), code);
  }

  @And("^user hit post answers endpoint with null answers$")
  public void userHitPostAnswersEndpointWithNullAnswers() {
    StudentQuestionWebRequest request = new StudentQuestionWebRequest(null, null);
    Response response = studentQuestionAPI.postAnswers(Collections.singletonList(request), authData.getCookie());
    studentQuestionData.setResponse(response);
  }

  @And("^student question error response body should contains these data :$")
  public void studentQuestionErrorResponseBodyShouldContainsTheseData(DataTable table) {
    Map<String, String> errors = table.asMap(String.class, String.class);
    errors.forEach((key, value) -> {
      assertTrue(studentQuestionData.getErrorResponse().getErrors().containsKey(key));
      assertEquals(studentQuestionData.getErrorResponse().getErrors().get(key).get(0), value);
    });
  }
}
