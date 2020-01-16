package com.future.function.qa.steps.Core.SharedCourse;

import com.future.function.qa.api.core.shared_course.SharedCourseAPI;
import com.future.function.qa.data.core.shared_course.SharedCourseData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

public class SharedCourseSteps extends BaseSteps {

  @Steps
  private SharedCourseAPI sharedCourseAPI;

  @Autowired
  private SharedCourseData sharedCourseData;

  @Given("^user prepare shared course request$")
  public void userPrepareSharedCourseRequest() {

    sharedCourseAPI.prepare();
  }

}
