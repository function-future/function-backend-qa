package com.future.function.qa.data.communication.logging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.communication.logging.LogMessageWebRequest;
import com.future.function.qa.model.request.communication.logging.LoggingRoomWebRequest;
import com.future.function.qa.model.request.communication.logging.TopicWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.communication.logging.LogMessageWebResponse;
import com.future.function.qa.model.response.communication.logging.LoggingRoomWebResponse;
import com.future.function.qa.model.response.communication.logging.TopicWebResponse;
import io.restassured.response.Response;
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

  private PagingResponse<LoggingRoomWebResponse> loggingRoomPagingResponse = new PagingResponse<>();

  private DataResponse<LoggingRoomWebResponse> loggingRoomDataResponse = new DataResponse<>();

  private PagingResponse<TopicWebResponse> topicPagingResponse = new PagingResponse<>();

  private DataResponse<TopicWebResponse> topicDataResponse = new DataResponse<>();

  private PagingResponse<LogMessageWebResponse> logMessagePagingResponse = new PagingResponse<>();

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

  public void setLoggingRoomPagingResponse(Response response){
    this.responseCode = response.getStatusCode();
    this.loggingRoomPagingResponse =  asPagingResponse(response, new TypeReference<PagingResponse<LoggingRoomWebResponse>>() {});
  }

  public void setOnlyDataLoggingRoomPagingResposne(Response response){
    this.loggingRoomPagingResponse =  asPagingResponse(response, new TypeReference<PagingResponse<LoggingRoomWebResponse>>() {});
  }

  public void setLoggingRoomDataResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.loggingRoomDataResponse = asDataResponse(response, new TypeReference<DataResponse<LoggingRoomWebResponse>>() {});
  }

  public void setTopicPagingResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.topicPagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<TopicWebResponse>>() {});
  }

  public void setOnlyDataTopicPagingResponse(Response response) {
    this.topicPagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<TopicWebResponse>>() {});
  }

  public void setTopicDataResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.topicDataResponse = asDataResponse(response, new TypeReference<DataResponse<TopicWebResponse>>() {});
  }

  public void setLogMessagesPagingResponse(Response response){
    this.responseCode = response.getStatusCode();
    this.logMessagePagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<LogMessageWebResponse>>() {});
  }

  public void setOnlyDataLogMessagesPagingResponse(Response response){
    this.logMessagePagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<LogMessageWebResponse>>() {});
  }

}


