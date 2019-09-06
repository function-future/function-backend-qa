package com.future.function.qa.steps.Auth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.api.auth.AuthAPI;
import com.future.function.qa.data.auth.AuthData;
import com.future.function.qa.model.response.auth.AuthWebResponse;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class AuthSteps extends BaseSteps {

  @Steps
  private AuthAPI authAPI;

  @Autowired
  private AuthData authData;

  @Then("^auth response should be ok and cookie is present$")
  public void authResponseShouldBeOkAndCookieIsPresent() throws Throwable {

    assertThat(authData.getResponseCode(), equalTo(200));
    assertThat(authData.getCookie(), notNullValue());
  }

  @Then("^auth response should be ok and cookie is unset$")
  public void authResponseShouldBeOkAndCookieIsUnset() throws Throwable {

    assertThat(authData.getResponseCode(), equalTo(200));
    assertThat(authData.getCookie()
        .getValue(), isEmptyString());
  }

  @Then("^auth response should be unauthorized$")
  public void authResponseShouldBeUnauthorized() throws Throwable {

    assertThat(authData.getResponseCode(), equalTo(401));
  }

  @When("^user do login with email \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void userDoLoginWithEmailAndPassword(String email, String password) throws Throwable {

    Response response = authAPI.login(authData.createRequest(email, password));

    authData.setResponse(authData.asDataResponse(response, new TypeReference<DataResponse<AuthWebResponse>>() {}));
    authData.setCookie(response.getDetailedCookie(authAPI.getCookieName()));
  }

  @When("^user hit auth endpoint$")
  public void userHitAuthEndpoint() throws Throwable {

    Response response = authAPI.getLoginStatus(authData.getCookie());

    authData.setResponse(authData.asDataResponse(response, new TypeReference<DataResponse<AuthWebResponse>>() {}));
  }

  @When("^user hit auth endpoint with cookie$")
  public void userHitAuthEndpointWithCookie() throws Throwable {

    Response response = authAPI.getLoginStatus(authData.getCookie());

    authData.setResponse(authData.asDataResponse(response, new TypeReference<DataResponse<AuthWebResponse>>() {}));
    authData.setCookie(response.getDetailedCookie(authAPI.getCookieName()));
  }

  @When("^user hit logout endpoint$")
  public void userHitLogoutEndpoint() throws Throwable {

    Response response = authAPI.logout(authData.getCookie());

    authData.setResponseCode(authData.asBaseResponse(response)
        .getCode());
    authData.setCookie(response.getDetailedCookie(authAPI.getCookieName()));
  }

  @Given("^user prepare auth request$")
  public void userPrepareAuthRequest() throws Throwable {

    authAPI.prepare();
  }
}
