package com.future.function.qa.steps.Core.Announcement;

import com.future.function.qa.api.core.announcement.AnnouncementAPI;
import com.future.function.qa.data.core.announcement.AnnouncementData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.announcement.AnnouncementWebResponse;
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

public class AnnouncementSteps extends BaseSteps {

  @Steps
  private AnnouncementAPI announcementAPI;

  @Autowired
  private AnnouncementData announcementData;

  @Autowired
  private ResourceData resourceData;

  @And("^announcement error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void announcementErrorResponseHasKeyAndValue(String key, String value) throws Throwable {

    ErrorResponse response = announcementData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @Then("^announcement response code should be (\\d+)$")
  public void announcementResponseCodeShouldBe(int expectedResponseCode) throws Throwable {

    assertThat(announcementData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And("^announcement response data should not be empty$")
  public void announcementResponseDataShouldNotBeEmpty() throws Throwable {

    PagingResponse<AnnouncementWebResponse> pagingResponse = announcementData.getPagingResponse();
    List<AnnouncementWebResponse> pagingResponseData = pagingResponse.getData();

    assertThat(pagingResponseData, not(empty()));
  }

  @And("^created announcement files should not be empty$")
  public void createdAnnouncementFilesShouldNotBeEmpty() throws Throwable {

    DataResponse<AnnouncementWebResponse> createdResponse = announcementData.getCreatedResponse();
    AnnouncementWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getFiles(), not(empty()));
  }

  @And("^created announcement title should be \"([^\"]*)\" and summary \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void createdAnnouncementTitleShouldBeAndSummaryAndDescription(String title, String summary, String description)
      throws Throwable {

    DataResponse<AnnouncementWebResponse> createdResponse = announcementData.getCreatedResponse();
    AnnouncementWebResponse createdResponseData = createdResponse.getData();

    assertThat(createdResponseData.getTitle(), equalTo(title));
    assertThat(createdResponseData.getSummary(), equalTo(summary));
    assertThat(createdResponseData.getDescription(), equalTo(description));
  }

  @And("^retrieved announcement files should not be empty$")
  public void retrievedAnnouncementFilesShouldNotBeEmpty() throws Throwable {

    DataResponse<AnnouncementWebResponse> retrievedResponse = announcementData.getRetrievedResponse();
    AnnouncementWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getFiles(), not(empty()));
  }

  @And("^retrieved announcement title should be \"([^\"]*)\" and summary \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void retrievedAnnouncementTitleShouldBeAndSummaryAndDescription(String title, String summary,
      String description) throws Throwable {

    DataResponse<AnnouncementWebResponse> retrievedResponse = announcementData.getRetrievedResponse();
    AnnouncementWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getTitle(), equalTo(title));
    assertThat(retrievedResponseData.getSummary(), equalTo(summary));
    assertThat(retrievedResponseData.getDescription(), equalTo(description));
  }

  @And("^user add resource id \"([^\"]*)\" to request$")
  public void userAddResourceIdToRequest(String resourceId) throws Throwable {

    announcementData.addRequestFiles(resourceId);
  }

  @And("^user add uploaded resource's id to announcement request$")
  public void userAddUploadedResourceSIdToAnnouncementRequest() throws Throwable {

    DataResponse<FileContentWebResponse> resourceDataCreatedResponse = resourceData.getCreatedResponse();
    FileContentWebResponse resourceDataCreatedResponseData = resourceDataCreatedResponse.getData();

    announcementData.addRequestFiles(resourceDataCreatedResponseData.getId());
  }

  @When(
      "^user create announcement request with title \"([^\"]*)\" and summary \"([^\"]*)\" and description \"([^\"]*)" +
          "\"$")
  public void userCreateAnnouncementRequestWithTitleAndSummaryAndDescription(String title, String summary,
      String description) throws Throwable {

    announcementData.createRequest(title, summary, description);
  }

  @And("^user hit announcement endpoint with page (\\d+) and size (\\d+)$")
  public void userHitAnnouncementEndpointWithPageAndSize(int page, int size) throws Throwable {

    Response response = announcementAPI.get(page, size, authData.getCookie());

    announcementData.setResponse(response);
  }

  @And("^user hit announcement endpoint with recorded id$")
  public void userHitAnnouncementEndpointWithRecordedId() throws Throwable {

    DataResponse<AnnouncementWebResponse> createdResponse = announcementData.getCreatedResponse();
    AnnouncementWebResponse createdResponseData = createdResponse.getData();

    Response response = announcementAPI.getDetail(createdResponseData.getId(), authData.getCookie());

    announcementData.setResponse(response);
  }

  @And("^user hit create announcement endpoint$")
  public void userHitCreateAnnouncementEndpoint() throws Throwable {

    Response response = announcementAPI.create(announcementData.getRequest(), authData.getCookie());

    announcementData.setResponse(response);

    if (announcementData.getResponseCode() == 201) {
      cleaner.append(
        DocumentName.ANNOUNCEMENT, announcementData.getCreatedResponse()
          .getData()
          .getId());
    }
  }

  @And("^user hit delete announcement endpoint with recorded id$")
  public void userHitDeleteAnnouncementEndpointWithRecordedId() throws Throwable {

    DataResponse<AnnouncementWebResponse> createdResponse = announcementData.getCreatedResponse();
    AnnouncementWebResponse createdResponseData = createdResponse.getData();

    Response response = announcementAPI.delete(createdResponseData.getId(), authData.getCookie());

    announcementData.setResponse(response);
  }

  @And("^user hit update announcement endpoint with recorded id$")
  public void userHitUpdateAnnouncementEndpointWithRecordedId() throws Throwable {

    DataResponse<AnnouncementWebResponse> createdResponse = announcementData.getCreatedResponse();
    AnnouncementWebResponse createdResponseData = createdResponse.getData();

    Response response =
        announcementAPI.update(createdResponseData.getId(), announcementData.getRequest(), authData.getCookie());

    announcementData.setResponse(response);
  }

  @Given("^user prepare announcement request$")
  public void userPrepareAnnouncementRequest() throws Throwable {

    announcementAPI.prepare();
  }

  @After
  public void cleanup() {

    cleaner.flushAll();
  }
}
