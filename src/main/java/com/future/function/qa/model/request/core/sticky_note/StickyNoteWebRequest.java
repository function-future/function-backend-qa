package com.future.function.qa.model.request.core.sticky_note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickyNoteWebRequest {

  private String description;

  private String title;
}
