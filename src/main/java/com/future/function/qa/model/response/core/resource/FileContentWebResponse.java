package com.future.function.qa.model.response.core.resource;

import java.util.Map;

import com.future.function.qa.model.response.embedded.AuthorWebResponse;
import com.future.function.qa.model.response.embedded.VersionWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileContentWebResponse {

  private AuthorWebResponse author;

  private Object file;

  private String id;

  private String name;

  private String parentId;

  private String type;

  private Map<Long, VersionWebResponse> versions;
}
