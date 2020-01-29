package com.future.function.qa.steps.Scoring.Student_Quiz;

import com.future.function.qa.api.scoring.student_quiz.StudentQuizAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.quiz.QuizData;
import com.future.function.qa.data.scoring.student_quiz.StudentQuizData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StudentQuizSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Autowired
  private QuizData quizData;

  @Autowired
  private StudentQuizData studentQuizData;

  @Steps
  private StudentQuizAPI studentQuizAPI;

  @And("^user get first quiz and store id$")
  public void userGetFirstQuizAndStoreId() {
    String quizId = quizData.getPagedResponse().getData().get(0).getId();
    studentQuizData.setQuizId(quizId);
  }

  @And("^user prepare student quiz request with stored quiz id and batchCode \"([^\"]*)\"$")
  public void userPrepareStudentQuizRequestWithStoredQuizIdAndBatchCode(String batchCode) throws Throwable {
    studentQuizData.setBatchCode(batchCode);
    studentQuizAPI.prepare(studentQuizData.getBatchCode(), studentQuizData.getQuizId());
  }

  @When("^user hit get or create student quiz endpoint$")
  public void userHitGetOrCreateStudentQuizEndpoint() {
    Response response = studentQuizAPI.getOrCreateStudentQuiz(authData.getCookie());
    studentQuizData.setResponse(response);
  }

  @Then("^student quiz error response code should be (\\d+)$")
  public void studentQuizErrorResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, studentQuizData.getErrorResponse().getCode());
  }

  @Then("^student quiz response code should be (\\d+)$")
  public void studentQuizResponseCodeShouldBe(int arg0) {
    assertEquals(arg0, studentQuizData.getSingleResponse().getCode());
  }

  @And("^student quiz response body quiz id should be the same as the stored quiz id$")
  public void studentQuizResponseBodyQuizIdShouldBeTheSameAsTheStoredQuizId() {
    assertEquals(studentQuizData.getQuizId(), studentQuizData.getSingleResponse().getData().getQuiz().getId());
  }

  @And("^student quiz response body id should not be null$")
  public void studentQuizResponseBodyIdShouldNotBeNull() {
    assertNotNull(studentQuizData.getSingleResponse().getData().getId());
  }
}
