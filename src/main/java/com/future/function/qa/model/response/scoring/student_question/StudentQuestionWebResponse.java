package com.future.function.qa.model.response.scoring.student_question;

import com.future.function.qa.model.response.scoring.option.OptionWebResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionWebResponse {

  private int number;

  private String text;

  private List<OptionWebResponse> options;

}
