package com.future.function.qa.model.response.scoring.student_question;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuizDetailWebResponse {

  private List<StudentQuestionWebResponse> questions;

  private Integer point;

  private Integer trials;

}
