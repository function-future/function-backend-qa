package com.future.function.qa.model.response.scoring.room;

import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.model.response.scoring.assignment.AssignmentWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomWebResponse {

  private String id;

  private UserWebResponse student;

  private AssignmentWebResponse assignment;

  private Integer point;

}
