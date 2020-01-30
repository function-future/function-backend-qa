package com.future.function.qa.steps.Core.UserDetail;

import com.future.function.qa.api.core.user_detail.ProfileAPI;
import com.future.function.qa.data.core.user_detail.ProfileData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProfileSteps extends BaseSteps {

  @Steps
  private ProfileAPI profileAPI;

  @Autowired
  private ProfileData profileData;

  @Given("^user prepare profile request$")
  public void userPrepareProfileRequest() {

    profileAPI.prepare();
  }

  @When("^user hit get profile endpoint$")
  public void userHitGetProfileEndpoint() throws Throwable {

    Response response = profileAPI.get(authData.getCookie());

    profileData.setResponse(response);
  }

  @Then("^profile response code should be (\\d+)$")
  public void profileResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(profileData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And("^profile response email should be \"([^\"]*)\", " +
       "name \"([^\"]*)\", role \"([^\"]*)\", address \"" +
       "([^\"]*)\", phone \"([^\"]*)\"$")
  public void profileResponseEmailShouldBeNameRoleAddressPhone(
    String email, String name, String role, String address, String phone
  ) throws Throwable {

    DataResponse<UserWebResponse> retrievedResponse =
      profileData.getRetrievedResponse();
    UserWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getEmail(), equalTo(email));
    assertThat(retrievedResponseData.getName(), equalTo(name));
    assertThat(retrievedResponseData.getRole(), equalTo(role));
    assertThat(retrievedResponseData.getAddress(), equalTo(address));
    assertThat(retrievedResponseData.getPhone(), equalTo(phone));
  }

  @And("^profile response picture should not be empty$")
  public void profileResponsePictureShouldNotBeEmpty() throws Throwable {

    DataResponse<UserWebResponse> retrievedResponse =
      profileData.getRetrievedResponse();
    UserWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getAvatar(), not(isEmptyString()));
  }

  @And("^profile response picture should be empty$")
  public void profileResponsePictureShouldBeEmpty() throws Throwable {

    DataResponse<UserWebResponse> retrievedResponse =
      profileData.getRetrievedResponse();
    UserWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(
      retrievedResponseData.getAvatar(), anyOf(isEmptyString(), nullValue()));
  }

}
