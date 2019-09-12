package com.future.function.qa.model.response.core.sticky_note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickyNoteWebResponse {

  private String description;

  private String id;

  private String title;

  private Long updatedAt;
}
