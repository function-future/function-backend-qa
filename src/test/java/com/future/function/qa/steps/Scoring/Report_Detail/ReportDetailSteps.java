package com.future.function.qa.steps.Scoring.Report_Detail;

import com.future.function.qa.api.scoring.report_detail.ReportDetailAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.report.ReportData;
import com.future.function.qa.data.scoring.report_detail.ReportDetailData;
import com.future.function.qa.model.request.scoring.report_detail.ScoreStudentWebRequest;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.Map;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReportDetailSteps extends BaseSteps {

  @Autowired
  private ReportData reportData;

  @Autowired
  private ReportDetailData reportDetailData;

  @Autowired
  private AuthData authData;

  @Steps
  private ReportDetailAPI reportDetailAPI;


  @And("^user prepare report detail request$")
  public void userPrepareReportDetailRequest() throws Throwable {
    reportDetailAPI.prepare();
  }

  @When("^user hit give score to students endpoint with first stored student id, minus \"([^\"]*)\" and score (\\d+)$")
  public void userHitGiveScoreToStudentsEndpointWithStoredStudentIdsAndTheseData(String minus, Integer score) {
    ScoreStudentWebRequest student = toScoreStudentWebRequest(minus, score);
    Response response = reportDetailAPI.giveScoreToStudents(student, authData.getCookie());
    reportDetailData.setResponse(response);
  }

  private ScoreStudentWebRequest toScoreStudentWebRequest(String minus, Integer score) {
    if (minus.equals("true")) {
      score = (-score);
    }
    String studentId = reportData.getSingleResponse().getData().getStudents().get(0).getId();
    return ScoreStudentWebRequest.builder()
        .studentId(studentId)
        .score(score)
        .build();
  }

  @Then("^report detail error response code should be (\\d+)$")
  public void reportDetailErrorResponseCodeShouldBe(int code) {
    assertEquals(reportDetailData.getErrorResponse().getCode(), code);
  }

  @And("^report detail error response body should have these data$")
  public void reportDetailErrorResponseBodyShouldHaveTheseData(DataTable dataTable) {
    Map<String, String> errors = dataTable.asMap(String.class, String.class);
    errors.forEach((key, value) -> {
      boolean result = reportDetailData.getErrorResponse().getErrors().containsKey(key);
      assertTrue(result);
      assertEquals(reportDetailData.getErrorResponse().getErrors().get(key).get(0), value);
    });
  }

  @Then("^report detail response code should be (\\d+)$")
  public void reportDetailResponseCodeShouldBe(int code) {
    assertEquals(reportDetailData.getSingleResponse().getCode(), code);
  }

  @And("^report detail response body score should be (\\d+)$")
  public void reportDetailResponseBodyScoreShouldBe(int expectedScore) {
    assertEquals(reportDetailData.getSingleResponse().getData().getPoint().intValue(), expectedScore);
  }
}
