package com.future.function.qa.model.request.scoring.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CopyAssignmentWebRequest {

  private String assignmentId;
  private String batchCode;

}
