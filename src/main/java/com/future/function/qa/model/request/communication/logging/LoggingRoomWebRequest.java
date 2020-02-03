package com.future.function.qa.model.request.communication.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Author: RickyKennedy
 * Created At:10:57 PM 2/3/2020
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggingRoomWebRequest {

  private String title;

  private String description;

  private List<String> members;

}
