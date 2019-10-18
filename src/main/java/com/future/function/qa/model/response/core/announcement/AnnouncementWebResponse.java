package com.future.function.qa.model.response.core.announcement;

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
public class AnnouncementWebResponse {

  private String id;

  private String title;

  private String summary;

  private String description;

  private List<EmbeddedFileWebResponse> files;

  private String announcementFileUrl;

  private Long updatedAt;
}

