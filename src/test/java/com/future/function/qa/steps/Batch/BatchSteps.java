package com.future.function.qa.steps.Batch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.batch.BatchAPI;
import com.future.function.qa.data.auth.AuthData;
import com.future.function.qa.data.batch.BatchData;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class BatchSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

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

  @When("^user hit create batch endpoint with name \"([^\"]*)\" and code \"([^\"]*)\"$")
  public void userHitCreateBatchEndpointWithNameAndCode(String name, String code) throws Throwable {

    Response response = batchAPI.create(batchData.createRequest(null, name, code), authData.getCookie());

    batchData.setResponse(response);
  }

  @Given("^user prepare batch request$")
  public void userPrepareBatchRequest() throws Throwable {

    batchAPI.prepare();
  }
}
