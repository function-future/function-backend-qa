package com.future.function.qa.model.response.scoring.comment;

import com.future.function.qa.model.response.embedded.AuthorWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentWebResponse {

  private String id;
  private AuthorWebResponse author;
  private String comment;
  private Long createdAt;

}
