package com.future.function.qa.steps.Core.File;

import com.future.function.qa.api.core.file.FileAPI;
import com.future.function.qa.data.core.file.FileData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.en.Given;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

public class FileSteps extends BaseSteps {

  @Steps
  private FileAPI fileAPI;

  @Autowired
  private FileData fileData;

  @Given("^user prepare file request$")
  public void userPrepareFileRequest() {

  }

}
