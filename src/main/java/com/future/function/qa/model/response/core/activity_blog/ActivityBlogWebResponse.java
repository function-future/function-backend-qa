package com.future.function.qa.model.response.core.activity_blog;

import com.future.function.qa.model.response.embedded.AuthorWebResponse;
import com.future.function.qa.model.response.embedded.EmbeddedFileWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBlogWebResponse {

  private AuthorWebResponse author;

  private String description;

  private List<EmbeddedFileWebResponse> files;

  private String id;

  private String title;

  private long updatedAt;
}
