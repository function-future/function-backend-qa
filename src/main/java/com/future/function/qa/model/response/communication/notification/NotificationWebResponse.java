package com.future.function.qa.model.response.communication.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationWebResponse {
  private String id;

  private String targetUser;

  private String title;

  private String description;

  private Long createdAt;
}
