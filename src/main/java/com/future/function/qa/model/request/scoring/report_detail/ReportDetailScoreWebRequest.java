package com.future.function.qa.model.request.scoring.report_detail;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailScoreWebRequest {

  private List<ScoreStudentWebRequest> scores;

}
