package com.future.function.qa.model.response.communication.reminder;

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
public class ReminderDetailResponse {

  private String id;

  private String title;

  private String description;

  private Boolean isRepeatedMonthly;

  private Integer monthlyDate;

  private List<String> repeatDays;

  private String time;

  private List<ParticipantDetailWebResponse> members;

}
