package com.future.function.qa.model.response.scoring.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionWebResponse {

  private String id;

  private String label;

  private String correct;

}
