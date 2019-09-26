package com.future.function.qa.steps.Core.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.user.UserAPI;
import com.future.function.qa.data.core.resource.ResourceData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.base.paging.Paging;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
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

  @And("^qa system do cleanup data for user with name \"([^\"]*)\" and email \"([^\"]*)\"$")
  public void qaSystemDoCleanupDataForUserWithNameAndEmail(String name, String email) throws Throwable {

    userAPI.prepare();
    userObtainUserIdWithNameAndEmail(name, email);
    userHitDeleteUserEndpointWithRecordedId();
  }

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

    Response response = userAPI.create(request, authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user hit delete user endpoint with recorded target user id$")
  public void userHitDeleteUserEndpointWithRecordedId() throws Throwable {

    Response response = userAPI.delete(userData.getTargetUserId(), authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user hit get user detail endpoint with recorded id$")
  public void userHitGetUserDetailEndpointWithRecordedId() throws Throwable {

    DataResponse<UserWebResponse> createdResponse = userData.getCreatedResponse();
    UserWebResponse createdResponseData = createdResponse.getData();

    Response response = userAPI.getDetail(createdResponseData.getId(), authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user hit get users by name endpoint with name part \"([^\"]*)\", page (\\d+), size (\\d+)$")
  public void userHitGetUsersByNameEndpointWithNamePartPageSize(String namePart, int page, int size) throws Throwable {

    Response response = userAPI.getByName(namePart, page, size, authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user hit get users endpoint with role \"([^\"]*)\", page (\\d+), size (\\d+)$")
  public void userHitGetUsersEndpointWithRolePageSize(String role, int page, int size) throws Throwable {

    Response response = userAPI.get(role, page, size, authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user hit update user endpoint with email \"([^\"]*)\", name \"([^\"]*)\", role \"([^\"]*)\", address \"" +
      "([^\"]*)\", phone \"([^\"]*)\", avatar \"([^\"]*)\", batch code \"([^\"]*)\", university \"([^\"]*)\"$")
  public void userHitUpdateUserEndpointWithEmailNameRoleAddressPhoneAvatarBatchCodeUniversity(String email, String name,
      String role, String address, String phone, String avatar, String batchCode, String university) throws Throwable {

    DataResponse<UserWebResponse> createdResponse = userData.getCreatedResponse();
    UserWebResponse createdResponseData = createdResponse.getData();
    String createdUserId = createdResponseData.getId();

    UserWebRequest request =
        userData.createRequest(createdUserId, email, name, role, address, phone, avatar, batchCode, university);

    Response response = userAPI.update(request, authData.getCookie());

    userData.setResponse(response);
  }

  @And("^user obtain user id with name \"([^\"]*)\" and email \"([^\"]*)\"$")
  public void userObtainUserIdWithNameAndEmail(String name, String email) throws Throwable {

    Response getByNameResponse = userAPI.getByName(name, 1, 100, authData.getCookie());

    userData.setResponse(getByNameResponse);

    PagingResponse<UserWebResponse> pagingResponse = userData.getPagingResponse();
    List<UserWebResponse> pagingResponseData = pagingResponse.getData();

    String targetUserId = pagingResponseData.stream()
        .filter(userWebResponse -> userWebResponse.getEmail()
            .equals(email))
        .map(UserWebResponse::getId)
        .findFirst()
        .orElse(StringUtils.EMPTY);

    userData.setTargetUserId(targetUserId);
  }

  @Given("^user prepare user request$")
  public void userPrepareUserRequest() throws Throwable {

    userAPI.prepare();
  }

  @Then("^user response code should be (\\d+)$")
  public void userResponseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(userData.getResponseCode(), equalTo(responseCode));
  }

  @And("^user response's email should be \"([^\"]*)\"$")
  public void userResponseSEmailShouldBe(String email) throws Throwable {

    DataResponse<UserWebResponse> retrievedResponse = userData.getRetrievedResponse();
    UserWebResponse retrievedResponseData = retrievedResponse.getData();

    assertThat(retrievedResponseData.getEmail(), equalTo(email));
  }

  @And("^user response's total elements should be (\\d+)$")
  public void userResponseSTotalElementsShouldBe(int totalElementSize) throws Throwable {

    PagingResponse<UserWebResponse> pagingResponse = userData.getPagingResponse();
    Paging paging = pagingResponse.getPaging();

    assertThat(paging.getTotalRecords(), equalTo(Integer.toUnsignedLong(totalElementSize)));
  }
}
