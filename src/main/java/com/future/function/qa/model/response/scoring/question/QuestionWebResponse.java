package com.future.function.qa.model.response.scoring.question;

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
public class QuestionWebResponse {

  private String id;

  private String label;

  private List<OptionWebResponse> options;

}
