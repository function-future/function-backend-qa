package com.future.function.qa.steps.Scoring.Comment;

import com.future.function.qa.api.scoring.comment.CommentAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.data.scoring.assignment.AssignmentData;
import com.future.function.qa.data.scoring.comment.CommentData;
import com.future.function.qa.data.scoring.room.RoomData;
import com.future.function.qa.model.request.scoring.comment.CommentWebRequest;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommentSteps extends BaseSteps {

  @Autowired
  private CommentData commentData;

  @Autowired
  private RoomData roomData;

  @Autowired
  private UserData userData;

  @Autowired
  private AssignmentData assignmentData;

  @Autowired
  private AuthData authData;

  @Steps
  private CommentAPI commentAPI;

  @And("^user prepare comment request$")
  public void userPrepareCommentRequest() {
    commentAPI.prepare(roomData.getStudentId(), roomData.getAssignmentId());
  }

  @When("^user hit create comment endpoint with comment \"([^\"]*)\"$")
  public void userHitCreateCommentEndpointWithComment(String comment) throws Throwable {
    CommentWebRequest request = commentData.createRequest(comment);
    Response response = commentAPI.createComment(request, authData.getCookie());
    commentData.setResponse(response);
  }

  @Then("^comment error response code should be (\\d+)$")
  public void commentErrorResponseCodeShouldBe(int code) {
    assertEquals(code, commentData.getErrorResponse().getCode());
  }

  @Then("^comment response code should be (\\d+)$")
  public void commentResponseCodeShouldBe(int code) {
    assertEquals(code, commentData.getSingleResponse().getCode());
  }

  @And("^comment response body comment should be \"([^\"]*)\"$")
  public void commentResponseBodyCommentShouldBe(String comment) throws Throwable {
    assertEquals(comment, commentData.getSingleResponse().getData().getComment());
  }

  @And("^comment response body author name should be \"([^\"]*)\"$")
  public void commentResponseBodyAuthorNameShouldBe(String authorName) throws Throwable {
    assertEquals(authorName, commentData.getSingleResponse().getData().getAuthor().getName());
  }

  @When("^user hit get all comment endpoint$")
  public void userHitGetAllCommentEndpoint() {
    Response response = commentAPI.getAllComment(authData.getCookie());
    commentData.setResponse(response);
  }

  @Then("^comment paging response code should be (\\d+)$")
  public void commentPagingResponseCodeShouldBe(int code) {
    assertEquals(commentData.getPagedResponse().getCode(), code);
  }

  @And("^comment paging response body should contains comment \"([^\"]*)\" and author name \"([^\"]*)\"$")
  public void commentPagingResponseBodyShouldContainsCommentAndAuthorName(String commentText,
      String authorName) throws Throwable {
    boolean result = commentData.getPagedResponse().getData().stream()
        .anyMatch(comment -> comment.getComment().equals(commentText) &&
            comment.getAuthor().getName().equals(authorName));
    assertTrue(result);
  }
}
