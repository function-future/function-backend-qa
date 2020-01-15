package com.future.function.qa.steps.Core.UserDetail;

import com.future.function.qa.api.core.user_detail.ChangePasswordAPI;
import com.future.function.qa.data.core.user_detail.ChangePasswordData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangePasswordSteps extends BaseSteps {

  @Steps
  private ChangePasswordAPI changePasswordAPI;

  @Autowired
  private ChangePasswordData changePasswordData;

  @Given("^user prepare change password request$")
  public void userPrepareChangePasswordRequest() {

    changePasswordAPI.prepare();
  }

  @When("^user hit change password endpoint with old " +
        "password \"([^\"]*)\" and new password \"" + "([^\"]*)\"$")
  public void userHitChangePasswordEndpointWithOldPasswordAndNewPassword(
    String oldPassword, String newPassword
  ) throws Throwable {

    Response response = changePasswordAPI.put(
      changePasswordData.createRequest(oldPassword, newPassword),
      authData.getCookie()
    );

    changePasswordData.setResponse(response);
  }

  @Then("^change password response code should be (\\d+)$")
  public void changePasswordResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(
      changePasswordData.getResponseCode(), equalTo(expectedResponseCode));
  }

}
