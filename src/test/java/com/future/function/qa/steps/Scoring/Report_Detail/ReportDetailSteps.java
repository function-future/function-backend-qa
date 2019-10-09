package com.future.function.qa.steps.Scoring.Report_Detail;

import com.future.function.qa.api.scoring.report_detail.ReportDetailAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.report.ReportData;
import com.future.function.qa.data.scoring.report_detail.ReportDetailData;
import com.future.function.qa.model.request.scoring.report_detail.ReportDetailScoreWebRequest;
import com.future.function.qa.model.request.scoring.report_detail.ScoreStudentWebRequest;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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


  @And("^user prepare report detail request with stored report id and batchCode \"([^\"]*)\"$")
  public void userPrepareReportDetailRequestWithStoredReportIdBatchCodeStoredStudentIdsAndTheseData(String arg0) throws Throwable {
    reportDetailAPI.prepare(arg0, reportData.getReportId());
  }

  @When("^user hit give score to students endpoint with stored student ids, minus \"([^\"]*)\" and these score$")
  public void userHitGiveScoreToStudentsEndpointWithStoredStudentIdsAndTheseData(String minus, List<Integer> scores) {
    List<ScoreStudentWebRequest> students = toReportDetailScoreWebRequest(minus, scores);
    ReportDetailScoreWebRequest request = reportDetailData.createRequest(students);
    Response response = reportDetailAPI.giveScoreToStudents(request, authData.getCookie());
    reportDetailData.setResponse(response);
  }

  private List<ScoreStudentWebRequest> toReportDetailScoreWebRequest(String minus, List<Integer> scores) {
    List<Integer> newScores;
    if(minus.equals("true")) {
      newScores = scores.stream()
          .map(integer -> (-integer))
          .collect(Collectors.toList());
    } else {
      newScores = new ArrayList<>(scores);
    }
    return reportData.getSingleResponse().getData().getStudents().stream()
        .map(student -> ScoreStudentWebRequest.builder()
            .studentId(student.getId())
            .score(newScores.remove(0))
            .build())
        .collect(Collectors.toList());
  }

  @Then("^report detail error response code should be (\\d+)$")
  public void reportDetailErrorResponseCodeShouldBe(int code) {
    assertEquals(reportDetailData.getErrorResponse().getCode(), code);
  }

  @And("^report detail error response body should have these data$")
  public void reportDetailErrorResponseBodyShouldHaveTheseData(DataTable dataTable) {
    Map<Integer, String> errors = dataTable.asMap(Integer.class, String.class);
    errors.forEach((key, value) -> {
      String errorKey = String.format("scores[%s].score", key);
      boolean result = reportDetailData.getErrorResponse().getErrors().containsKey(errorKey);
      assertTrue(result);
      assertEquals(reportDetailData.getErrorResponse().getErrors().get(errorKey).get(0), value);
    });
  }

  @Then("^report detail list response code should be (\\d+)$")
  public void reportDetailListResponseCodeShouldBe(int code) {
    assertEquals(reportDetailData.getListedResponse().getCode(), code);
  }

  @And("^report detail list response body size should be more than (\\d+)$")
  public void reportDetailListResponseBodySizeShouldBeMoreThan(int size) {
    assertTrue(reportDetailData.getListedResponse().getData().size() > size);
  }
}
