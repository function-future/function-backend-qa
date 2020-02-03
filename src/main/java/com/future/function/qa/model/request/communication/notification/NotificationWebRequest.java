package com.future.function.qa.model.request.communication.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationWebRequest {

  private String targetUser;

  private String title;

  private String description;

}
