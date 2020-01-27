package com.future.function.qa.steps;

import com.future.function.qa.FunctionQAApplication;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.util.Cleaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = FunctionQAApplication.class,
                      initializers =
                        ConfigFileApplicationContextInitializer.class)
public abstract class BaseSteps {

  @Autowired
  protected AuthData authData;

  @Autowired
  protected Cleaner cleaner;

}
