package com.future.function.qa.model.request.question_bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankWebRequest {

  private String id;
  private String title;
  private String description;

}
