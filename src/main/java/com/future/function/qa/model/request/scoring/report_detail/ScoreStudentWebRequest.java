package com.future.function.qa.model.request.scoring.report_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreStudentWebRequest {

  private String studentId;

  private Integer score;

}
