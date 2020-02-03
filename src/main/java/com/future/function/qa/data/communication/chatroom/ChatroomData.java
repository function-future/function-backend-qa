package com.future.function.qa.data.communication.chatroom;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.communication.chatroom.ChatroomLimitWebRequest;
import com.future.function.qa.model.request.communication.chatroom.ChatroomWebRequest;
import com.future.function.qa.model.request.communication.chatroom.MessageWebRequest;
import com.future.function.qa.model.response.base.BaseResponse;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.communication.chatroom.ChatroomDetailWebResponse;
import com.future.function.qa.model.response.communication.chatroom.ChatroomListWebResponse;
import com.future.function.qa.model.response.communication.chatroom.MessageWebResponse;
import io.restassured.response.Response;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatroomData extends BaseData {

  private DataResponse<ChatroomDetailWebResponse> detailResponse = new DataResponse<>();

  private PagingResponse<ChatroomListWebResponse> pagingResponse = new PagingResponse<>();

  private PagingResponse<MessageWebResponse> messagePagingResponse = new PagingResponse<>();

  public ChatroomWebRequest createChatroomRequest(String name, String picture, List<String> members) {
    return ChatroomWebRequest.builder()
            .name(name)
            .picture(picture == null ? Collections.emptyList() : Collections.singletonList(picture))
            .members(members)
            .build();
  }

  public ChatroomLimitWebRequest createChatroomLimitRequest(long limit) {
    return ChatroomLimitWebRequest.builder().limit(limit).build();
  }

  public MessageWebRequest createMessageRequest(String message) {
    return MessageWebRequest.builder().message(message).build();
  }

  public void setDetailResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.detailResponse = asDataResponse(response, new TypeReference<DataResponse<ChatroomDetailWebResponse>>() {});
  }

  public void setMessagePagingResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.messagePagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<MessageWebResponse>>() {});
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<ChatroomListWebResponse>>() {});
  }
}
