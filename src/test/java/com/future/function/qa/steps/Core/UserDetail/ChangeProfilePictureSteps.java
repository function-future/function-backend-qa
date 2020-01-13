package com.future.function.qa.steps.Core.UserDetail;

import com.future.function.qa.api.core.user_detail.ChangeProfilePictureAPI;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.data.core.user_detail.ChangeProfilePictureData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

public class ChangeProfilePictureSteps extends BaseSteps {

  @Steps
  private ChangeProfilePictureAPI changeProfilePictureAPI;

  @Autowired
  private ChangeProfilePictureData changeProfilePictureData;

  @Autowired
  private ResourceData resourceData;

  @Given("^user prepare change profile picture request$")
  public void userPrepareChangeProfilePictureRequest() {

    changeProfilePictureAPI.prepare();
  }

  @And("^user prepare change profile picture request " +
       "with avatar id \"([^\"]*)\"$")
  public void userPrepareChangeProfilePictureRequestWithAvatarId(
    String avatarId
  ) throws Throwable {

    changeProfilePictureData.createRequest(
      Arrays.asList(this.getFromResourceDataOrDefault(avatarId)));
  }

  private String getFromResourceDataOrDefault(String defaultValue) {

    return Optional.of(defaultValue)
      .filter("default"::equals)
      .map(ignored -> resourceData.getCreatedResponse())
      .map(DataResponse::getData)
      .map(FileContentWebResponse::getId)
      .orElse(defaultValue);
  }

  @And("^user hit change profile picture endpoint$")
  public void userHitChangeProfilePictureEndpoint() throws Throwable {

    Response response = changeProfilePictureAPI.put(
      changeProfilePictureData.getRequest(), authData.getCookie());

    changeProfilePictureData.setResponse(response);
  }

  @Then("^change profile picture endpoint response code " + "should be (\\d+)$")
  public void changeProfilePictureEndpointResponseCodeShouldBe(
    int expectedResponseCode
  ) throws Throwable {

    assertThat(
      changeProfilePictureData.getResponseCode(),
      equalTo(expectedResponseCode)
    );
  }

  @And("^change profile picture error response should have key \"([^\"]*)\" " +
       "and value \"([^\"]*)\"$")
  public void changeProfilePictureErrorResponseShouldHaveKeyAndValue(
    String key, String value
  ) throws Throwable {

    ErrorResponse response = changeProfilePictureData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), hasItem(value));
  }

  @And("^qa system do cleanup data for recorded avatars$")
  public void qaSystemDoCleanupDataForRecordedAvatars() throws Throwable {

    changeProfilePictureData.setRequest(null);
  }

}
