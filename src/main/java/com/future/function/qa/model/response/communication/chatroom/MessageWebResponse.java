package com.future.function.qa.model.response.communication.chatroom;

import com.future.function.qa.model.response.embedded.ParticipantWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageWebResponse {

  private String id;

  private ParticipantWebResponse sender;

  private String text;

  private Long time;

}
