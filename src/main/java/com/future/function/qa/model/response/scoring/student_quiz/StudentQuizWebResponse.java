package com.future.function.qa.model.response.scoring.student_quiz;

import com.future.function.qa.model.response.scoring.quiz.QuizWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuizWebResponse {

  private String id;

  private QuizWebResponse quiz;

  private Integer trials;

}
