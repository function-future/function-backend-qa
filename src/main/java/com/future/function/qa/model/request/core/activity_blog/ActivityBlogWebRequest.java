package com.future.function.qa.model.request.core.activity_blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityBlogWebRequest {

  private String description;

  private List<String> files;

  private String title;
}
