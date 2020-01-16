package com.future.function.qa.model.request.core.shared_course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharedCourseWebRequest {

  private String originBatch;

  private List<String> courses;

}
