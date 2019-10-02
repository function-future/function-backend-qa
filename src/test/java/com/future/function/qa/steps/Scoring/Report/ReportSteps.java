package com.future.function.qa.steps.Scoring.Report;

import com.future.function.qa.api.scoring.report.ReportAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.scoring.report.ReportData;
import com.future.function.qa.model.request.scoring.report.ReportWebRequest;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.DataTable;
import cucumber.api.Pending;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReportSteps extends BaseSteps {

  @Autowired
  private ReportData reportData;

  @Autowired
  private AuthData authData;

  @Steps
  private ReportAPI reportAPI;


  @And("^user prepare report request with batchCode \"([^\"]*)\"$")
  public void userPrepareReportRequestWithBatchCode(String batchCode) throws Throwable {
    reportAPI.prepare(batchCode);
  }

  @When("^user hit get students within batch$")
  public void userHitGetStudentsWithinBatch() {
    Response response = reportAPI.getStudentsWithinBatch(authData.getCookie());
    reportData.setResponse(response);
  }

  @Then("^report error response code should be (\\d+)$")
  public void reportErrorResponseCodeShouldBe(int code) {
    assertEquals(reportData.getErrorResponse().getCode(), code);
  }

  @Then("^student paging response code should be (\\d+)$")
  public void studentPagingResponseCodeShouldBe(int code) {
    assertEquals(reportData.getStudentPagingResponse().getCode(), code);
  }

  @And("^student paging response body should not be null$")
  public void studentPagingResponseBodyShouldNotBeNull() {
    assertNotNull(reportData.getStudentPagingResponse().getData());
  }

  @When("^user hit create report with name \"([^\"]*)\" and description \"([^\"]*)\" and \"([^\"]*)\"$")
  public void userHitCreateReportWithNameAndDescriptionAndPreviousFetchedStudents(String name, String description, String condition) throws Throwable {
    List<String> studentIds = new ArrayList<>();
    if(condition.equals("students")) {
      studentIds = reportData.getStudentPagingResponse().getData()
          .stream().limit(2)
          .map(UserWebResponse::getId)
          .collect(Collectors.toList());
    }
    ReportWebRequest request = reportData.createRequest(name, description, studentIds);
    Response response = reportAPI.createReport(request, authData.getCookie());
    reportData.setResponse(response);
  }

  @Then("^report response code should be (\\d+)$")
  public void reportResponseCodeShouldBe(int code) {
    if(reportData.getSingleResponse() == null) {
      throw new PendingException("Data already created with the same students!");
    }
    assertEquals(reportData.getSingleResponse().getCode(), code);
  }

  @And("^report response body should have these data$")
  public void reportResponseBodyShouldHaveTheseData(DataTable dataTable) {
    Map<String, String> expected = dataTable.asMap(String.class, String.class);
    assertEquals(reportData.getSingleResponse().getData().getName(), expected.get("name"));
    assertEquals(reportData.getSingleResponse().getData().getDescription(), expected.get("description"));
  }

  @And("^report error response body should contains these data$")
  public void reportErrorResponseBodyShouldContainsTheseData(DataTable dataTable) {
    Map<String, String> errors = dataTable.asMap(String.class, String.class);
    errors.forEach((key, value) -> {
      assertTrue(key, reportData.getErrorResponse().getErrors().containsKey(key));
      assertEquals(value, reportData.getErrorResponse().getErrors().get(key).get(0));
    });
  }

  @And("^user hit get report with any id from all report response$")
  public void userHitGetReportWithAnyIdFromAllReportResponse() {
    String reportId = reportData.getPagedResponse().getData()
        .stream()
        .findAny()
        .get()
        .getId();
    Response response = reportAPI.getReport(reportId, authData.getCookie());
    reportData.setResponse(response);
  }

  @And("^report response body should have not equal with these data$")
  public void reportResponseBodyShouldHaveNotEqualWithTheseData(DataTable dataTable) {
    Map<String, String> data = dataTable.asMap(String.class, String.class);
    List<Integer> rangeSize = new ArrayList<>(Arrays.asList(data.get("studentCount").split("-")))
        .stream()
        .map(Integer::valueOf)
        .collect(Collectors.toList());
    assertNotEquals(data.get("name"), reportData.getSingleResponse().getData().getName());
    assertNotEquals(data.get("description"), reportData.getSingleResponse().getData().getDescription());
    assertTrue(rangeSize.get(0) < reportData.getSingleResponse().getData().getStudentCount() &&
        rangeSize.get(1) > reportData.getSingleResponse().getData().getStudentCount());
  }

  @When("^user hit get all report endpoint$")
  public void userHitGetAllReportEndpoint() {
    Response response = reportAPI.getAllReport(authData.getCookie());
    reportData.setResponse(response);
  }

  @Then("^report paging response code should be (\\d+)$")
  public void reportPagingResponseCodeShouldBe(int code) {
    assertEquals(code, reportData.getPagedResponse().getCode());
  }
}
