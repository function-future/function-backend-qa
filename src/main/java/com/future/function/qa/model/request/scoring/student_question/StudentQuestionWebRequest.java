package com.future.function.qa.model.request.scoring.student_question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionWebRequest {

  private int number;

  private String optionId;

}
