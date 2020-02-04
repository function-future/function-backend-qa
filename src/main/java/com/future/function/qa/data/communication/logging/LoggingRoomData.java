package com.future.function.qa.data.communication.logging;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.communication.logging.LogMessageWebRequest;
import com.future.function.qa.model.request.communication.logging.LoggingRoomWebRequest;
import com.future.function.qa.model.request.communication.logging.TopicWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.communication.logging.LogMessageWebResponse;
import com.future.function.qa.model.response.communication.logging.LoggingRoomWebResponse;
import com.future.function.qa.model.response.communication.logging.TopicWebResponse;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author: RickyKennedy
 * Created At:10:03 AM 2/4/2020
 */
@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoggingRoomData extends BaseData {

  private PagingResponse<LoggingRoomWebResponse> pagingLoggingRoomResponse = new PagingResponse<>();

  private DataResponse<LoggingRoomWebResponse> dataLoggingRoomResponse = new DataResponse<>();

  private PagingResponse<TopicWebResponse> pagingTopicResponse = new PagingResponse<>();

  private DataResponse<TopicWebResponse> dataTopicResponse = new DataResponse<>();

  private PagingResponse<LogMessageWebResponse> pagingLogMessageResponse = new PagingResponse<>();

  public LoggingRoomWebRequest createLoggingRoomRequest(
    String title,
    String description,
    List<String> membersId
  ) {
    return LoggingRoomWebRequest.builder()
      .title(title)
      .description(description)
      .members(membersId)
      .build();
  }

  public TopicWebRequest createTopicRequest(String title){
    return TopicWebRequest.builder()
      .title(title)
      .build();
  }

  public LogMessageWebRequest createLogMessageRequest(String text){
    return LogMessageWebRequest.builder()
      .text(text)
      .build();
  }

}


