package com.future.function.qa.steps.Core.Announcement;

import com.future.function.qa.api.core.announcement.AnnouncementAPI;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;

public class AnnouncementSteps extends BaseSteps {

  @Steps
  private AnnouncementAPI announcementAPI;

  @Given("^user prepare announcement request$")
  public void userPrepareAnnouncementRequest() throws Throwable {

    announcementAPI.prepare();
  }
}
