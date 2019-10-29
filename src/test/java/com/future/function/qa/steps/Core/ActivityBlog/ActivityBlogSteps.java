package com.future.function.qa.steps.Core.ActivityBlog;

import com.future.function.qa.api.core.activity_blog.ActivityBlogAPI;
import com.future.function.qa.data.core.activity_blog.ActivityBlogData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.activity_blog.ActivityBlogWebResponse;
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
import static org.hamcrest.Matchers.*;

public class ActivityBlogSteps extends BaseSteps {

  @Steps
  private ActivityBlogAPI activityBlogAPI;

  @Autowired
  private ActivityBlogData activityBlogData;

  @Autowired
  private ResourceData resourceData;

  @Given("^user prepare activity blog request$")
  public void userPrepareActivityBlogRequest() {

    activityBlogAPI.prepare();
  }

  @When("^user create activity blog request with title \"([^\"]*)\" and " +
        "description \"([^\"]*)\"$")
  public void userCreateActivityBlogRequestWithTitleAndDescription(
    String title, String description
  ) throws Throwable {

    activityBlogData.createRequest(title, description);
  }

  @And("^user hit create activity blog endpoint$")
  public void userHitCreateActivityBlogEndpoint() throws Throwable {

    Response response = activityBlogAPI.create(
      activityBlogData.getRequest(), authData.getCookie());

    activityBlogData.setResponse(response);
  }

  @Then("^activity blog response code should be (\\d+)$")
  public void activityBlogResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(
      activityBlogData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And(
    "^created activity blog title should be \"([^\"]*)\" and description \"" +
    "([^\"]*)\"$")
  public void createdActivityBlogTitleShouldBeAndDescription(
    String expectedTitle, String expectedDescription
  ) throws Throwable {

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getTitle(), equalTo(expectedTitle));
    assertThat(createdResponseData.getDescription(),
               equalTo(expectedDescription)
    );
  }

  @And("^user add uploaded resource's id to activity blog request$")
  public void userAddUploadedResourceSIdToActivityBlogRequest()
    throws Throwable {

    DataResponse<FileContentWebResponse> resourceDataCreatedResponse =
      resourceData.getCreatedResponse();
    FileContentWebResponse resourceDataCreatedResponseData =
      resourceDataCreatedResponse.getData();

    activityBlogData.addRequestFiles(resourceDataCreatedResponseData.getId());
  }

  @And("^created activity blog files should not be empty$")
  public void createdActivityBlogFilesShouldNotBeEmpty() throws Throwable {

    DataResponse<ActivityBlogWebResponse> retrievedResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getFiles(), not(empty()));
  }

  @And("^user add resource id \"([^\"]*)\" to activity blog request$")
  public void userAddResourceIdToActivityBlogRequest(String resourceId)
    throws Throwable {

    activityBlogData.addRequestFiles(resourceId);
  }

  @And("^activity blog error response has key \"([^\"]*)\" and value \"" +
       "([^\"]*)\"$")
  public void activityBlogErrorResponseHasKeyAndValue(String key, String value)
    throws Throwable {

    ErrorResponse response = activityBlogData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

}
