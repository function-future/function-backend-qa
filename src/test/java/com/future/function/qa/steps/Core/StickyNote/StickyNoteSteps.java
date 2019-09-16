package com.future.function.qa.steps.Core.StickyNote;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.core.sticky_note.StickyNoteAPI;
import com.future.function.qa.data.core.auth.AuthData;
import com.future.function.qa.data.core.sticky_note.StickyNoteData;
import com.future.function.qa.model.response.base.ErrorResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.sticky_note.StickyNoteWebResponse;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

public class StickyNoteSteps extends BaseSteps {

  @Autowired
  private AuthData authData;

  @Steps
  private StickyNoteAPI stickyNoteAPI;

  @Autowired
  private StickyNoteData stickyNoteData;

  @And("^sticky note error response has key \"([^\"]*)\" and value \"([^\"]*)\"$")
  public void stickyNoteErrorResponseHasKeyAndValue(String key, String value) throws Throwable {

    ErrorResponse response = stickyNoteData.getErrorResponse();
    Map<String, List<String>> errors = response.getErrors();

    assertThat(errors.get(key), notNullValue());
    assertThat(errors.get(key), equalTo(Collections.singletonList(value)));
  }

  @Then("^sticky note response code should be (\\d+)$")
  public void stickyNoteResponseCodeShouldBe(int responseCode) throws Throwable {

    assertThat(stickyNoteData.getResponseCode(), equalTo(responseCode));
  }

  @And("^sticky note title should be \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void stickyNoteTitleShouldBeAndDescription(String title, String description) throws Throwable {

    PagingResponse<StickyNoteWebResponse> response = stickyNoteData.getRetrievedResponse();
    List<StickyNoteWebResponse> data = response.getData();

    assertThat(data, not(empty()));
    assertThat(data.size(), equalTo(1));

    StickyNoteWebResponse stickyNoteWebResponse = data.get(0);

    assertThat(stickyNoteWebResponse.getId(), notNullValue());
    assertThat(stickyNoteWebResponse.getTitle(), equalTo(title));
    assertThat(stickyNoteWebResponse.getDescription(), equalTo(description));
    assertThat(stickyNoteWebResponse.getUpdatedAt(), notNullValue());
  }

  @When("^user hit create sticky note endpoint with title \"([^\"]*)\" and description \"([^\"]*)\"$")
  public void userHitCreateStickyNoteEndpointWithTitleAndDescription(String title, String description)
      throws Throwable {

    Response response =
        stickyNoteAPI.createStickyNote(stickyNoteData.createRequest(title, description), authData.getCookie());

    stickyNoteData.setResponse(response);
  }

  @When("^user hit sticky note endpoint$")
  public void userHitStickyNoteEndpoint() throws Throwable {

    Response response = stickyNoteAPI.getStickyNote();

    stickyNoteData.setResponse(response);
  }

  @Given("^user prepare sticky note request$")
  public void userPrepareStickyNoteRequest() throws Throwable {

    stickyNoteAPI.prepare();
  }
}

