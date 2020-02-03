package com.future.function.qa.steps.Core.File;

import com.future.function.qa.api.core.file.FileAPI;
import com.future.function.qa.data.core.file.FileData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.file.DataPageResponse;
import com.future.function.qa.model.response.core.file.FileWebResponse;
import com.future.function.qa.model.response.core.file.PathWebResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.model.response.embedded.VersionWebResponse;
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

    if (fileData.getResponseCode() == 201) {
      cleaner.append(DocumentName.FILE, fileData.getCreatedResponse()
        .getData()
        .getContent()
        .getId());
    }
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

  @When("^user hit files/folders endpoint with parent id \"([^\"]*)\"$")
  public void userHitFilesFoldersEndpointWithParentId(String parentId)
    throws Throwable {

    Response response = fileAPI.get(parentId, authData.getCookie());

    fileData.setResponse(response);
  }

  @And("^file/folder response should not be empty$")
  public void fileFolderResponseShouldNotBeEmpty() throws Throwable {

    DataPageResponse<FileWebResponse<List<FileContentWebResponse>>>
      retrievedResponses = fileData.getRetrievedResponses();
    FileWebResponse<List<FileContentWebResponse>> retrievedResponsesData =
      retrievedResponses.getData();
    List<FileContentWebResponse> content = retrievedResponsesData.getContent();

    assertThat(content, notNullValue());
    assertThat(content, not(empty()));
  }

  @And("^file/folder response path ids should contain \"([^\"]*)\"$")
  public void fileFolderResponsePathIdsShouldContain(
    String expectedContainedPath
  ) throws Throwable {

    DataPageResponse<FileWebResponse<List<FileContentWebResponse>>>
      retrievedResponses = fileData.getRetrievedResponses();
    FileWebResponse<List<FileContentWebResponse>> retrievedResponsesData =
      retrievedResponses.getData();
    List<PathWebResponse> paths = retrievedResponsesData.getPaths();

    assertThat(paths, notNullValue());

    boolean pathContainsExpectedContainedPath = paths.stream()
      .map(PathWebResponse::getId)
      .anyMatch(expectedContainedPath::equals);
    assertThat(pathContainsExpectedContainedPath, equalTo(true));
  }

  @And("^user hit get file/folder endpoint with recorded id and parent id \"" +
       "([^\"]*)\"$")
  public void userHitGetFileFolderEndpointWithRecordedIdAndParentId(
    String parentId
  ) throws Throwable {

    DataResponse<FileWebResponse<FileContentWebResponse>> createdResponse =
      fileData.getCreatedResponse();
    FileWebResponse<FileContentWebResponse> createdResponseData =
      createdResponse.getData();
    FileContentWebResponse createdResponseDataContent =
      createdResponseData.getContent();

    Response response = fileAPI.getFileDetail(
      createdResponseDataContent.getId(), parentId, authData.getCookie());

    fileData.setResponse(response);
  }

  @And("^retrieved file/folder name should be \"([^\"]*)\"$")
  public void retrievedFileFolderNameShouldBe(String expectedName)
    throws Throwable {

    DataResponse<FileWebResponse<FileContentWebResponse>> retrievedResponse =
      fileData.getRetrievedResponse();
    FileWebResponse<FileContentWebResponse> retrievedResponseData =
      retrievedResponse.getData();
    FileContentWebResponse retrievedResponseDataContent =
      retrievedResponseData.getContent();

    assertThat(retrievedResponseDataContent.getName(), equalTo(expectedName));
  }

  @And("^retrieved file version should have key (\\d+)$")
  public void retrievedFileVersionShouldHaveKey(int expectedOwnedKey)
    throws Throwable {

    DataResponse<FileWebResponse<FileContentWebResponse>> retrievedResponse =
      fileData.getRetrievedResponse();
    FileWebResponse<FileContentWebResponse> retrievedResponseData =
      retrievedResponse.getData();
    FileContentWebResponse retrievedResponseDataContent =
      retrievedResponseData.getContent();
    Map<Long, VersionWebResponse> fileVersions =
      retrievedResponseDataContent.getVersions();

    assertThat(
      fileVersions.containsKey(Integer.toUnsignedLong(expectedOwnedKey)),
      equalTo(true)
    );
  }

  @And("^user hit update file/folder endpoint with recorded id and parent id " +
       "\"([^\"]*)\"$")
  public void userHitUpdateFileFolderEndpointWithRecordedIdAndParentId(
    String parentId
  ) throws Throwable {

    DataResponse<FileWebResponse<FileContentWebResponse>> createdResponse =
      fileData.getCreatedResponse();
    FileWebResponse<FileContentWebResponse> createdResponseData =
      createdResponse.getData();
    FileContentWebResponse createdResponseDataContent =
      createdResponseData.getContent();

    Response response = fileAPI.update(createdResponseDataContent.getId(),
                                       parentId, fileData.getFile(),
                                       fileData.getRequestAsJson(),
                                       authData.getCookie()
    );

    fileData.setResponse(response);
  }

  @And("^user hit delete file/folder endpoint with recorded id and parent id " +
       "\"([^\"]*)\"$")
  public void userHitDeleteFileFolderEndpointWithRecordedIdAndParentId(
    String parentId
  ) throws Throwable {

    DataResponse<FileWebResponse<FileContentWebResponse>> createdResponse =
      fileData.getCreatedResponse();
    FileWebResponse<FileContentWebResponse> createdResponseData =
      createdResponse.getData();
    FileContentWebResponse createdResponseDataContent =
      createdResponseData.getContent();

    Response response = fileAPI.delete(
      createdResponseDataContent.getId(), parentId, authData.getCookie());

    fileData.setResponse(response);
  }

  @After
  public void cleanup() {

    cleaner.flushAll();
  }

}
