package com.future.function.qa.steps.Core.SharedCourse;

import com.future.function.qa.api.core.shared_course.DiscussionAPI;
import com.future.function.qa.data.core.shared_course.DiscussionData;
import com.future.function.qa.data.core.shared_course.SharedCourseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
import com.future.function.qa.model.response.core.shared_course.DiscussionWebResponse;
import com.future.function.qa.steps.BaseSteps;
import com.future.function.qa.util.DocumentName;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

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

    if (discussionData.getResponseCode() == 201) {
      cleaner.append(DocumentName.DISCUSSION,
                     discussionData.getCreatedResponse()
                       .getData()
                       .getId()
      );
    }
  }

  @Then("^discussion response code should be (\\d+)$")
  public void discussionResponseCodeShouldBe(int expectedResponseCode)
    throws Throwable {

    assertThat(discussionData.getResponseCode(), equalTo(expectedResponseCode));
  }

  @And("^user hit discussion endpoint with page (\\d+) and size (\\d+)$")
  public void userHitDiscussionEndpointWithPageAndSize(int page, int size)
    throws Throwable {

    Response response = discussionAPI.get(page, size, authData.getCookie());

    discussionData.setResponse(response);
  }

  @And("^retrieved discussion response data should not be empty$")
  public void retrievedDiscussionResponseDataShouldNotBeEmpty()
    throws Throwable {

    PagingResponse<DiscussionWebResponse> pagingResponse =
      discussionData.getPagingResponse();

    assertThat(pagingResponse.getData(), not(empty()));
  }

  @After
  public void cleanup() {

    cleaner.flushAll();
  }

}
