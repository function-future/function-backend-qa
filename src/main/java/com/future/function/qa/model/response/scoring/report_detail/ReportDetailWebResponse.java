package com.future.function.qa.model.response.scoring.report_detail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.future.function.qa.model.response.base.paging.Paging;
import com.future.function.qa.model.response.scoring.summary.SummaryWebResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDetailWebResponse {

  private String studentId;

  private String studentName;

  private String batchCode;

  private String university;

  private String avatar;

    private List<SummaryWebResponse> scores;

  private Integer point;

  private Integer totalPoint;

  private Paging paging;

}
