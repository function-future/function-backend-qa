package com.future.function.qa.model.response.communication.chatroom;

import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import com.future.function.qa.model.response.embedded.ParticipantDetailWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomDetailWebResponse {

  private String id;

  private String name;

  private List<ParticipantDetailWebResponse> members;

  private String type;

  private FileContentWebResponse picture;

}
