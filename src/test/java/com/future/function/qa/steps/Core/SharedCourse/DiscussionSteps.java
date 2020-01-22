package com.future.function.qa.steps.Core.SharedCourse;

import com.future.function.qa.api.core.shared_course.DiscussionAPI;
import com.future.function.qa.data.core.shared_course.DiscussionData;
import com.future.function.qa.data.core.shared_course.SharedCourseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DiscussionSteps extends BaseSteps {

  @Steps
  private DiscussionAPI discussionAPI;

  @Autowired
  private SharedCourseData sharedCourseData;

  @Autowired
  private DiscussionData discussionData;

  @And("^user prepare discussion request with target " +
       "batch code \"([^\"]*)\" and course \"([^\"]*)\"$")
  public void userPrepareDiscussionRequestWithTargetBatchCodeAndCourse(
    String targetBatchCode, String courseId
  ) throws Throwable {

    String targetCourseId = Optional.of(courseId)
      .filter("first-recorded-shared-course-id"::equals)
      .map(ignored -> sharedCourseData.getCreatedResponse())
      .map(DataResponse::getData)
      .map(responses -> responses.get(0))
      .map(CourseWebResponse::getId)
      .orElse(courseId);

    discussionAPI.prepare(targetBatchCode, targetCourseId);
  }

  @When("^user hit create discussion with comment \"([^\"]*)\"$")
  public void userHitCreateDiscussionWithComment(String comment)
    throws Throwable {

    Response response = discussionAPI.create(
      discussionData.createRequest(comment), authData.getCookie());

    discussionData.setResponse(response);
  }

  @Then("^discussion response code should be (\\d+)$")
  public void discussionResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(discussionData.getResponseCode(), equalTo(expectedResponseCode));
  }

}
