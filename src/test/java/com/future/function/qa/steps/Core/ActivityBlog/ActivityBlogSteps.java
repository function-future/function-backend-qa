package com.future.function.qa.steps.Core.ActivityBlog;

import com.future.function.qa.api.core.activity_blog.ActivityBlogAPI;
import com.future.function.qa.data.core.activity_blog.ActivityBlogData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.activity_blog.ActivityBlogWebResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.steps.BaseSteps;
import com.future.function.qa.util.DocumentName;
import cucumber.api.java.After;
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

    if (activityBlogData.getResponseCode() == 201) {
      cleaner.append(
        DocumentName.ACTIVITY_BLOG, activityBlogData.getCreatedResponse()
          .getData()
          .getId());
    }
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

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getFiles(), not(empty()));
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

  @And("^user hit activity blog endpoint with page (\\d+) and size (\\d+)$")
  public void userHitActivityBlogEndpointWithPageAndSize(int page, int size)
    throws Throwable {

    Response response = activityBlogAPI.get(page, size, authData.getCookie());

    activityBlogData.setResponse(response);
  }

  @And("^retrieved activity blog response data should not be empty$")
  public void retrievedActivityBlogResponseDataShouldNotBeEmpty() throws Throwable {

    PagingResponse<ActivityBlogWebResponse> pagingResponse =
      activityBlogData.getPagingResponse();
    List<ActivityBlogWebResponse> pagingResponseData = pagingResponse.getData();

    assertThat(pagingResponseData, not(empty()));
  }

  @And("^retrieved activity blog response data should be empty$")
  public void retrievedActivityBlogResponseDataShouldBeEmpty() throws Throwable {

    PagingResponse<ActivityBlogWebResponse> pagingResponse =
      activityBlogData.getPagingResponse();
    List<ActivityBlogWebResponse> pagingResponseData = pagingResponse.getData();

    assertThat(pagingResponseData, empty());
  }

  @And("^user hit activity blog endpoint with recorded id$")
  public void userHitActivityBlogEndpointWithRecordedId() throws Throwable {

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    Response response = activityBlogAPI.getDetail(
      createdResponseData.getId(), authData.getCookie());

    activityBlogData.setResponse(response);
  }

  @And("^retrieved activity blog title should be \"([^\"]*)\" and description" +
       " \"([^\"]*)\"$")
  public void retrievedActivityBlogTitleShouldBeAndDescription(
    String expectedTitle, String expectedDescription
  ) throws Throwable {

    DataResponse<ActivityBlogWebResponse> retrievedResponse =
      activityBlogData.getRetrievedResponse();
    ActivityBlogWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getTitle(), equalTo(expectedTitle));
    assertThat(retrievedResponseData.getDescription(),
               equalTo(expectedDescription)
    );
  }

  @And("^retrieved activity blog files should not be empty$")
  public void retrievedActivityBlogFilesShouldNotBeEmpty() throws Throwable {

    DataResponse<ActivityBlogWebResponse> retrievedResponse =
      activityBlogData.getRetrievedResponse();
    ActivityBlogWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getFiles(), not(empty()));
  }

  @And("^user hit update activity blog endpoint with recorded id$")
  public void userHitUpdateActivityBlogEndpointWithRecordedId()
    throws Throwable {

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    Response response = activityBlogAPI.update(createdResponseData.getId(),
                                               activityBlogData.getRequest(),
                                               authData.getCookie()
    );
    activityBlogData.setResponse(response);
  }

  @And("^retrieved activity blog files should be empty$")
  public void retrievedActivityBlogFilesShouldBeEmpty() throws Throwable {

    DataResponse<ActivityBlogWebResponse> retrievedResponse =
      activityBlogData.getRetrievedResponse();
    ActivityBlogWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getFiles(), empty());
  }

  @And("^user hit delete activity blog endpoint with recorded id$")
  public void userHitDeleteActivityBlogEndpointWithRecordedId()
    throws Throwable {

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    Response response = activityBlogAPI.delete(
      createdResponseData.getId(), authData.getCookie());
    activityBlogData.setResponse(response);
  }

  @And("^user hit activity blog endpoint with page (\\d+) and size (\\d+) and" +
       " user id \"([^\"]*)\"$")
  public void userHitActivityBlogEndpointWithPageAndSizeAndUserId(
    int page, int size, String userId
  ) throws Throwable {

    String authorId = userId.equals("current user") ? authData.getResponse()
      .getData()
      .getId() : userId;

    Response response = activityBlogAPI.getByAuthorId(
      page, size, authorId, authData.getCookie());

    activityBlogData.setResponse(response);
  }

  @And("^activity blog response data should be empty$")
  public void activityBlogResponseDataShouldBeEmpty() throws Throwable {

    DataResponse<ActivityBlogWebResponse> createdResponse =
      activityBlogData.getCreatedResponse();
    ActivityBlogWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getFiles(), empty());
  }

  @After
  public void cleanup() {

    cleaner.flushAll();
  }

}
