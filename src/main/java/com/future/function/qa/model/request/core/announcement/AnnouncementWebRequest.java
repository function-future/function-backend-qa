package com.future.function.qa.model.request.core.announcement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementWebRequest {

  private String title;

  private String summary;

  private String description;

  private List<String> files;
}
