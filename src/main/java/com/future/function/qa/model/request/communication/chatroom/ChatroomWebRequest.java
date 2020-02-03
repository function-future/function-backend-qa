package com.future.function.qa.model.request.communication.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatroomWebRequest {

  private String name;

  private List<String> members;

  private List<String> picture;

}
