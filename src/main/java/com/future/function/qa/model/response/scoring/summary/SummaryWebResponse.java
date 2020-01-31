package com.future.function.qa.model.response.scoring.summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryWebResponse {

  private String type;

  private String title;

  private int point;

}
