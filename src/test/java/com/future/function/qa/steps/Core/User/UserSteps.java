package com.future.function.qa.steps.Core.User;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.user.UserAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class UserSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Steps
  private UserAPI userAPI;

  @Autowired
  private UserData userData;

  @Given("^user prepare user request$")
  public void userPrepareUserRequest() throws Throwable {

    userAPI.prepare();
  }
}
