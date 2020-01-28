package com.future.function.qa.model.response.question_bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankWebResponse {

  private String id;
  private String title;
  private String description;

}
