package com.future.function.qa.steps.Core.Batch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.batch.BatchAPI;
import com.future.function.qa.data.core.batch.BatchData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.batch.BatchWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class BatchSteps extends BaseSteps {

  @Steps
  private BatchAPI batchAPI;

  @Autowired
  private BatchData batchData;

  @And("^batch error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void batchErrorResponseHasKeyAndValue(String key, String value) throws Throwable {

    ErrorResponse response = batchData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @Then("^batch response code should be (\\d+)$")
  public void batchResponseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(batchData.getResponseCode(), equalTo(responseCode));
  }

  @And("^batch response's code should be \"([^\"]*)\"$")
  public void batchResponseSCodeShouldBe(String expectedCode) throws Throwable {

    DataResponse<BatchWebResponse> retrievedResponse = batchData.getSingleResponse();
    BatchWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getCode(), equalTo(expectedCode));
  }

  @And("^batch response's name should be \"([^\"]*)\"$")
  public void batchResponseSNameShouldBe(String expectedName) throws Throwable {

    DataResponse<BatchWebResponse> retrievedResponse = batchData.getSingleResponse();
    BatchWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getName(), equalTo(expectedName));
  }

  @And("^batch response should contain code \"([^\"]*)\"$")
  public void batchResponseShouldContainCode(String expectedCode) throws Throwable {

    boolean isAnyCodeMatchingExpectedCode = batchData.getPagedResponse()
        .getData()
        .stream()
        .map(BatchWebResponse::getCode)
        .anyMatch(batchCode -> batchCode.equals(expectedCode));

    assertThat(isAnyCodeMatchingExpectedCode, equalTo(true));
  }

  @And("^batch response should contain name \"([^\"]*)\"$")
  public void batchResponseShouldContainName(String expectedName) throws Throwable {

    boolean isAnyNameMatchingExpectedName = batchData.getPagedResponse()
        .getData()
        .stream()
        .map(BatchWebResponse::getName)
        .anyMatch(batchName -> batchName.equals(expectedName));

    assertThat(isAnyNameMatchingExpectedName, equalTo(true));
  }

  @When("^user hit batch detail endpoint with recorded id$")
  public void userHitBatchDetailEndpointWithRecordedId() throws Throwable {

    DataResponse<BatchWebResponse> createdResponse = batchData.getSingleResponse();
    BatchWebResponse createdResponseData = createdResponse.getData();

    Response response = batchAPI.getDetail(createdResponseData.getId(), authData.getCookie());

    batchData.setResponse(response);
  }

  @When("^user hit batch endpoint with page (\\d+) and size (\\d+)$")
  public void userHitBatchEndpointWithPageAndSize(int page, int size) throws Throwable {

    Response response = batchAPI.get(page, size, authData.getCookie());

    batchData.setResponse(response);
  }

  @When("^user hit create batch endpoint with name \"([^\"]*)\" and code \"([^\"]*)\"$")
  public void userHitCreateBatchEndpointWithNameAndCode(String name, String code) throws Throwable {

    Response response = batchAPI.create(batchData.createRequest(null, name, code), authData.getCookie());

    batchData.setResponse(response);
  }

  @And("^user hit delete batch endpoint with recorded id$")
  public void userHitDeleteBatchEndpointWithRecordedId() throws Throwable {

    DataResponse<BatchWebResponse> createdResponse = batchData.getSingleResponse();
    BatchWebResponse createdResponseData = createdResponse.getData();

    Response response = batchAPI.delete(createdResponseData.getId(), authData.getCookie());

    batchData.setResponse(response);
  }

  @And("^user hit edit batch endpoint with recorded id and name \"([^\"]*)\" and code \"([^\"]*)\"$")
  public void userHitEditBatchEndpointWithRecordedIdAndNameAndCode(String name, String code) throws Throwable {

    DataResponse<BatchWebResponse> createdResponse = batchData.getSingleResponse();
    BatchWebResponse createdResponseData = createdResponse.getData();

    Response response =
        batchAPI.edit(batchData.createRequest(createdResponseData.getId(), name, code), authData.getCookie());

    batchData.setResponse(response);
  }

  @Given("^user prepare batch request$")
  public void userPrepareBatchRequest() throws Throwable {

    batchAPI.prepare();
  }
}
