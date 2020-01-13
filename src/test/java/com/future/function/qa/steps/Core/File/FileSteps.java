package com.future.function.qa.steps.Core.File;

import com.future.function.qa.api.core.file.FileAPI;
import com.future.function.qa.data.core.file.FileData;
import com.future.function.qa.model.response.base.ErrorResponse;
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

public class FileSteps extends BaseSteps {

  @Steps
  private FileAPI fileAPI;

  @Autowired
  private FileData fileData;

  @Given("^user prepare file request$")
  public void userPrepareFileRequest() {

    fileAPI.prepare();
  }

  @And("^user hit create file/folder endpoint with parent id \"([^\"]*)\"$")
  public void userHitCreateFileFolderEndpointWithParentId(String parentId)
    throws Throwable {

    Response response = fileAPI.create(parentId, fileData.getFile(),
                                       fileData.getRequestAsJson(),
                                       authData.getCookie()
    );

    fileData.setResponse(response);
  }

  @Then("^file/folder response code should be (\\d+)$")
  public void fileFolderResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(fileData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @When("^user create \"([^\"]*)\" \"([^\"]*)\"$")
  public void userCreate(String type, String pathOrName) throws Throwable {

    fileData.createRequest(pathOrName, type);
  }

  @And("^file/folder error response has key \"([^\"]*)\" and value \"([^\"]*)" +
       "\"$")
  public void fileFolderErrorResponseHasKeyAndValue(String key, String value)
    throws Throwable {

    ErrorResponse response = fileData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

}
