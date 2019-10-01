package com.future.function.qa.steps.Scoring.Student_Question;

import com.future.function.qa.api.scoring.student_question.StudentQuestionAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.quiz.QuizData;
import com.future.function.qa.data.scoring.student_question.StudentQuestionData;
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

public class StudentQuestionSteps extends BaseSteps {

  @Autowired
  private QuizData quizData;

  @Autowired
  private AuthData authData;

  @Autowired
  private StudentQuestionData studentQuestionData;

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
}
