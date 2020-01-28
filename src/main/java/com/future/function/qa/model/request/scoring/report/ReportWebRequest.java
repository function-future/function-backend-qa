package com.future.function.qa.model.request.scoring.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportWebRequest {

  private String name;
  private String description;
  private List<String> students;

}
