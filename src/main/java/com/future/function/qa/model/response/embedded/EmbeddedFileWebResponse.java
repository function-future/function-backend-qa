package com.future.function.qa.model.response.embedded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedFileWebResponse {

  private String id;

  private File file;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class File {

    private String full;

    private String thumbnail;
  }
}
