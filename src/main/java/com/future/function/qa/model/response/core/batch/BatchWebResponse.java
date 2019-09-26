package com.future.function.qa.model.response.core.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchWebResponse {

  private String code;

  private String id;

  private String name;
}
