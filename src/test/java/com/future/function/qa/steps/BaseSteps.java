package com.future.function.qa.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.future.function.qa.FunctionQAApplication;
import com.future.function.qa.data.core.auth.AuthData;

@ContextConfiguration(classes = FunctionQAApplication.class)
public abstract class BaseSteps {

  @Autowired
  protected AuthData authData;
}
