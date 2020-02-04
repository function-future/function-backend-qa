package com.future.function.qa.steps.Communication.Logging;

import com.future.function.qa.data.communication.chatroom.ChatroomData;
import com.future.function.qa.data.communication.logging.LoggingRoomData;
import com.future.function.qa.data.core.user.UserData;
import com.future.function.qa.steps.BaseSteps;
import cucumber.api.java.After;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: RickyKennedy
 * Created At:10:48 AM 2/4/2020
 */
public class LoggingRoomSteps extends BaseSteps {

  @Autowired
  private UserData userData;

  @Autowired
  private LoggingRoomData loggingRoomData;

  @After
  public void cleanup() {
    cleaner.flushAll();
  }


}
