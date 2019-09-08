package com.future.function.qa.steps.Core.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.resource.ResourceAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class ResourceSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Steps
  private ResourceAPI resourceAPI;

  @Autowired
  private ResourceData resourceData;

  @Then("^resource response code should be (\\d+)$")
  public void resourceResponseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(resourceData.getResponseCode(), equalTo(responseCode));
  }

  @And("^resource should have file \"([^\"]*)\" url$")
  public void resourceShouldHaveFileUrl(String path) throws Throwable {

    DataResponse<FileContentWebResponse> createdResponse = resourceData.getCreatedResponse();
    FileContentWebResponse createdResponseData = createdResponse.getData();

    Map<String, String> fileData = (Map<String, String>) createdResponseData.getFile();

    assertThat(fileData, hasKey(path));
    assertThat(fileData.get(path), notNullValue());
  }

  @And("^resource should not have file \"([^\"]*)\" url$")
  public void resourceShouldNotHaveFileUrl(String path) throws Throwable {

    DataResponse<FileContentWebResponse> createdResponse = resourceData.getCreatedResponse();
    FileContentWebResponse createdResponseData = createdResponse.getData();

    Map<String, String> fileData = (Map<String, String>) createdResponseData.getFile();

    assertThat(fileData.get(path), nullValue());
  }

  @And("^user hit post resource endpoint$")
  public void userHitPostResourceEndpoint() throws Throwable {

    Response response =
        resourceAPI.post("file", resourceData.getFile(), resourceData.getOrigin(), authData.getCookie());

    resourceData.setResponse(response);
  }

  @Given("^user prepare resource request$")
  public void userPrepareResourceRequest() throws Throwable {

    resourceAPI.prepare();
  }

  @When("^user select file \"([^\"]*)\" to be uploaded to origin \"([^\"]*)\"$")
  public void userSelectFileToBeUploadedToOrigin(String filePath, String origin) throws Throwable {

    resourceData.createRequest(filePath, origin);
  }
}
