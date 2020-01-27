package com.future.function.qa.model.request.scoring.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CopyQuizWebRequest {

  private String batchCode;
  private String quizId;

}
