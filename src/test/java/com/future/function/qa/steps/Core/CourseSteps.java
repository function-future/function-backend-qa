package com.future.function.qa.steps.Core;

import com.future.function.qa.api.core.course.CourseAPI;
import com.future.function.qa.data.core.course.CourseData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

public class CourseSteps extends BaseSteps {

  @Steps
  private CourseAPI courseAPI;

  @Autowired
  private CourseData courseData;

  @Autowired
  private ResourceData resourceData;

  @Given("^user prepare course request$")
  public void userPrepareCourseRequest() {

    courseAPI.prepare();
  }

  @When("^user create course request with title \"([^\"]*)\" and description " +
        "\"([^\"]*)\"$")
  public void userCreateCourseRequestWithTitleAndDescription(
    String title, String description
  ) throws Throwable {

    courseData.createRequest(title, description);
  }

  @And("^user hit create course endpoint$")
  public void userHitCreateCourseEndpoint() throws Throwable {

    Response response = courseAPI.create(
      courseData.getRequest(), authData.getCookie());

    courseData.setResponse(response);
  }

  @Then("^course response code should be (\\d+)$")
  public void courseResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(courseData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And("^user add resource id \"([^\"]*)\" to course request$")
  public void userAddResourceIdToCourseRequest(String resourceId)
    throws Throwable {

    courseData.addRequestMaterials(resourceId);
  }

  @And("^course error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void courseErrorResponseHasKeyAndValue(String key, String value)
    throws Throwable {

    ErrorResponse response = courseData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @And("^user add uploaded resource's id to course request$")
  public void userAddUploadedResourceSIdToCourseRequest() throws Throwable {

    DataResponse<FileContentWebResponse> resourceDataCreatedResponse =
      resourceData.getCreatedResponse();
    FileContentWebResponse resourceDataCreatedResponseData =
      resourceDataCreatedResponse.getData();

    courseData.addRequestMaterials(resourceDataCreatedResponseData.getId());
  }

  @And("^created course title should be \"([^\"]*)\" and description \"" +
       "([^\"]*)\"$")
  public void createdCourseTitleShouldBeAndDescription(
    String expectedTitle, String expectedDescription
  ) throws Throwable {

    DataResponse<CourseWebResponse> createdResponse =
      courseData.getCreatedResponse();
    CourseWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getTitle(), equalTo(expectedTitle));
    assertThat(createdResponseData.getDescription(),
               equalTo(expectedDescription)
    );
  }

  @And("^created course material and material id should not be null$")
  public void createdCourseMaterialAndMaterialIdShouldNotBeNull()
    throws Throwable {

    DataResponse<CourseWebResponse> createdResponse =
      courseData.getCreatedResponse();
    CourseWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getMaterial(), notNullValue());
    assertThat(createdResponseData.getMaterialId(), notNullValue());
  }

}
