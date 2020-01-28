package com.future.function.qa.model.response.scoring.report;

import com.future.function.qa.model.response.core.user.UserWebResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportWebResponse {

  private String id;
  private String name;
  private String description;
  private String batchCode;
  private int studentCount;
  private long uploadedDate;
  private List<UserWebResponse> students;

}
