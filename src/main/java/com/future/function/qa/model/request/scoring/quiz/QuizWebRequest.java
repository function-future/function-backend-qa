package com.future.function.qa.model.request.scoring.quiz;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizWebRequest {

  private String title;
  private String description;
  private long timeLimit;
  private int trials;
  private long startDate;
  private long endDate;
  private int questionCount;
  private List<String> questionBanks;

}
