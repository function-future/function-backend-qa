package com.future.function.qa.steps.Core.SharedCourse;

import com.future.function.qa.api.core.shared_course.SharedCourseAPI;
import com.future.function.qa.data.core.batch.BatchData;
import com.future.function.qa.data.core.course.CourseData;
import com.future.function.qa.data.core.shared_course.SharedCourseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

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

  @And("^user create shared course request for batch \"([^\"]*)\"$")
  public void userCreateSharedCourseRequestForBatch(String originBatchCode)
    throws Throwable {

    boolean isCopyFromMaster = StringUtils.isEmpty(originBatchCode);
    if (isCopyFromMaster) {
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

}
