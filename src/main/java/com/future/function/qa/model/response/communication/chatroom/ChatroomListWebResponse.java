package com.future.function.qa.model.response.communication.chatroom;

import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.model.response.embedded.LastMessageWebResponse;
import com.future.function.qa.model.response.embedded.ParticipantWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomListWebResponse {

  private String id;

  private LastMessageWebResponse lastMessage;

  private String name;

  private List<ParticipantWebResponse> participants;

  private FileContentWebResponse picture;

  private String type;

}
