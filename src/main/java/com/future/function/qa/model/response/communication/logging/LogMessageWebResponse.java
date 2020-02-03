package com.future.function.qa.model.response.communication.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessageWebResponse {

  private String id;

  private String text;

  private long createdAt;

  private String senderName;

  private String senderAvatar;

}
