package com.future.function.qa.model.response.core.shared_course;

import com.future.function.qa.model.response.embedded.AuthorWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscussionWebResponse {

  private String id;

  private String comment;

  private AuthorWebResponse author;

  private Long createdAt;

}
