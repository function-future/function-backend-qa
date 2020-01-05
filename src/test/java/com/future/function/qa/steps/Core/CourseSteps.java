package com.future.function.qa.steps.Core;

import com.future.function.qa.api.core.course.CourseAPI;
import com.future.function.qa.data.core.course.CourseData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseSteps extends BaseSteps {

  @Steps
  private CourseAPI courseAPI;

  @Autowired
  private CourseData courseData;

  @Given("^user prepare course request$")
  public void userPrepareCourseRequest() {

    courseAPI.prepare();
  }

}
