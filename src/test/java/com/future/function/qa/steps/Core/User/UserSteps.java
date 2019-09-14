package com.future.function.qa.steps.Core.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.user.UserAPI;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class UserSteps extends BaseSteps {

  @Autowired
  private ResourceData resourceData;

  @Steps
  private UserAPI userAPI;

  @Autowired
  private UserData userData;

  @And("^user error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void userErrorResponseHasKeyAndValue(String key, String value) throws Throwable {

    ErrorResponse response = userData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @And("^user hit create user endpoint with email \"([^\"]*)\", name \"([^\"]*)\", role \"([^\"]*)\", address \"" +
      "([^\"]*)\", phone \"([^\"]*)\", avatar \"([^\"]*)\", batch code \"([^\"]*)\", university \"([^\"]*)\"$")
  public void userHitCreateUserEndpointWithEmailNameRoleAddressPhoneAvatarBatchCodeUniversity(String email, String name,
      String role, String address, String phone, String avatar, String batchCode, String university) throws Throwable {

    String requestAvatarId = Optional.of(avatar)
        .filter(String::isEmpty)
        .map(ignored -> resourceData.getCreatedResponse())
        .map(DataResponse::getData)
        .map(FileContentWebResponse::getId)
        .orElse(avatar);

    UserWebRequest request =
        userData.createRequest(null, email, name, role, address, phone, requestAvatarId, batchCode, university);

    Response response = userAPI.createUser(request, authData.getCookie());

    userData.setResponse(response);
  }

  @Given("^user prepare user request$")
  public void userPrepareUserRequest() throws Throwable {

    userAPI.prepare();
  }

  @Then("^user response code should be (\\d+)$")
  public void userResponseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(userData.getResponseCode(), equalTo(responseCode));
  }
}
