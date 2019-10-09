package com.future.function.qa.model.response.scoring.report_detail;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailWebResponse {

  private String studentId;

  private String studentName;

  private String batchCode;

  private String university;

  private String avatar;

  private Integer point;

  private Integer totalPoint;

}
