package com.future.function.qa.steps.Sanity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.future.function.qa.api.sanity.SanityAPI;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class SanitySteps {

  private Response response;

  @Steps
  private SanityAPI sanityAPI;

  @Then("^response code should be (\\d+)$")
  public void responseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(response.getStatusCode(), equalTo(responseCode));
  }

  @When("^user hit Google endpoint$")
  public void userHitGoogleEndpoint() throws Throwable {

    response = sanityAPI.get();
  }

  @Given("^user prepare request$")
  public void userPrepareRequest() throws Throwable {

    sanityAPI.prepare();
  }
}
