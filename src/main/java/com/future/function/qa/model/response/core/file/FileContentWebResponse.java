package com.future.function.qa.model.response.core.file;

import com.future.function.qa.model.response.embedded.AuthorWebResponse;
import com.future.function.qa.model.response.embedded.VersionWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContentWebResponse {

  private String id;

  private String type;

  private String name;

  private Object file;

  private Map<Long, VersionWebResponse> versions;

  private String parentId;

  private AuthorWebResponse author;

}
