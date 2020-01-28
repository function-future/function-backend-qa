package com.future.function.qa.model.request.scoring.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionWebRequest {

  private String id;

  private String label;

  private boolean correct;

}
