package com.future.function.qa.steps.Core.SharedCourse;

import com.future.function.qa.api.core.shared_course.SharedCourseAPI;
import com.future.function.qa.data.core.batch.BatchData;
import com.future.function.qa.data.core.course.CourseData;
import com.future.function.qa.data.core.shared_course.SharedCourseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
import com.future.function.qa.steps.BaseSteps;
import com.future.function.qa.util.DocumentName;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SharedCourseSteps extends BaseSteps {

  @Steps
  private SharedCourseAPI sharedCourseAPI;

  @Autowired
  private SharedCourseData sharedCourseData;

  @Autowired
  private BatchData batchData;

  @Autowired
  private CourseData courseData;

  @Given("^user prepare shared course request with target batch code " +
         "\"([^\"]*)\"$")
  public void userPrepareSharedCourseRequest(String targetBatchCode) {

    sharedCourseAPI.prepare(targetBatchCode);
  }

  @And("^user create shared course request from batch \"([^\"]*)\"$")
  public void userCreateSharedCourseRequestFromBatch(String originBatchCode)
    throws Throwable {

    boolean copyFromMaster = StringUtils.isEmpty(originBatchCode);
    if (copyFromMaster) {
      originBatchCode = null;
    } else if (originBatchCode.equals("recorded-batch-code")) {
      originBatchCode = batchData.getSingleResponse()
        .getData()
        .getCode();
    }

    sharedCourseData.createRequest(originBatchCode);
  }

  @And("^user add \"([^\"]*)\" to shared course request$")
  public void userAddRecordedCourseIdToSharedCourseRequest(String courseId)
    throws Throwable {

    if (courseId.equals("recorded-course-id")) {
      DataResponse<CourseWebResponse> courseDataCreatedResponse =
        courseData.getCreatedResponse();
      CourseWebResponse courseDataCreatedResponseData =
        courseDataCreatedResponse.getData();
      courseId = courseDataCreatedResponseData.getId();
    } else if (courseId.equals("first-recorded-shared-course-id")) {
      courseId = sharedCourseData.getCreatedResponse()
        .getData()
        .get(0)
        .getId();
    }

    sharedCourseData.addCourseIdToRequest(courseId);
  }

  @And("^user hit create shared course endpoint for batch \"([^\"]*)\"$")
  public void userHitCreateSharedCourseEndpointForBatch(String targetBatchCode)
    throws Throwable {

    if (targetBatchCode.equals("recorded-batch-code")) {
      targetBatchCode = batchData.getSingleResponse()
        .getData()
        .getCode();
    }

    Response response = sharedCourseAPI.create(
      targetBatchCode, sharedCourseData.getRequest(), authData.getCookie());

    sharedCourseData.setResponse(response);

    if (sharedCourseData.getResponseCode() == 201) {
      for (CourseWebResponse resp : sharedCourseData.getCreatedResponse()
        .getData()) {
        cleaner.append(DocumentName.SHARED_COURSE, resp.getId());
      }
    }
  }

  @Then("^shared course response code should be (\\d+)$")
  public void sharedCourseResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(
      sharedCourseData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And("^shared course error response has key \"([^\"]*)\" and value \"" +
       "([^\"]*)\"$")
  public void sharedCourseErrorResponseHasKeyAndValue(String key, String value)
    throws Throwable {

    ErrorResponse errorResponse = sharedCourseData.getErrorResponse();
    Map<String, List<String>> errors = errorResponse.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @After
  public void cleanup() {

    cleaner.flushAll();
  }

  @And("^user hit shared course endpoint with page (\\d+) and size (\\d+)$")
  public void userHitSharedCourseEndpointWithPageAndSize(int page, int size)
    throws Throwable {

    Response response = sharedCourseAPI.get(page, size, authData.getCookie());

    sharedCourseData.setResponse(response);
  }

  @And("^shared course response data should not be empty$")
  public void sharedCourseResponseDataShouldNotBeEmpty() throws Throwable {

    PagingResponse<CourseWebResponse> pagingResponse =
      sharedCourseData.getPagingResponse();

    assertThat(pagingResponse.getData(), not(empty()));
  }

  @And("^user hit shared course endpoint with \"([^\"]*)\"$")
  public void userHitSharedCourseEndpointWith(String sharedCourseId)
    throws Throwable {

    String id = Optional.of(sharedCourseId)
      .filter(i -> i.equals("first-recorded-shared-course-id"))
      .map(ignored -> sharedCourseData.getCreatedResponse())
      .map(DataResponse::getData)
      .map(responses -> responses.get(0))
      .map(CourseWebResponse::getId)
      .orElse(sharedCourseId);

    Response response = sharedCourseAPI.getDetail(id, authData.getCookie());

    sharedCourseData.setResponse(response);
  }

  @And("^user hit update shared course endpoint with \"([^\"]*)\"$")
  public void userHitUpdateSharedCourseEndpointWith(String sharedCourseId)
    throws Throwable {

    String id = Optional.of(sharedCourseId)
      .filter(i -> i.equals("first-recorded-shared-course-id"))
      .map(ignored -> sharedCourseData.getCreatedResponse())
      .map(DataResponse::getData)
      .map(responses -> responses.get(0))
      .map(CourseWebResponse::getId)
      .orElse(sharedCourseId);

    Response response = sharedCourseAPI.update(
      id, courseData.getRequest(), authData.getCookie());

    sharedCourseData.setResponse(response);
  }

  @And(
    "^retrieved shared course response data should have title \"([^\"]*)\" " +
    "and description \"([^\"]*)\"$")
  public void retrievedSharedCourseResponseDataShouldHaveTitleAndDescription(
    String expectedTitle, String expectedDescription
  ) throws Throwable {

    DataResponse<CourseWebResponse> retrievedResponse =
      sharedCourseData.getRetrievedResponse();
    CourseWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getTitle(), equalTo(expectedTitle));
    assertThat(
      retrievedResponseData.getDescription(), equalTo(expectedDescription));
  }

}
