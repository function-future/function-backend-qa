package com.future.function.qa.model.request.scoring.question;

import com.future.function.qa.model.request.scoring.option.OptionWebRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWebRequest {

  private String label;
  private List<OptionWebRequest> options;

}
