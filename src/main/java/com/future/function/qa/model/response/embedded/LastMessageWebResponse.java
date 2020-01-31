package com.future.function.qa.model.response.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LastMessageWebResponse {

  private String message;

  private Boolean seen;

  private Long time;

}
