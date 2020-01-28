package com.future.function.qa.model.response.scoring.quiz;

import com.future.function.qa.model.response.base.BaseResponse;
import com.future.function.qa.model.response.question_bank.QuestionBankWebResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizWebResponse extends BaseResponse {

  private String id;

  private String title;

  private String description;

  private Long startDate;

  private Long endDate;

  private Long timeLimit;

  private Integer trials;

  private Integer questionCount;

  private List<QuestionBankWebResponse> questionBanks;

  private String batchCode;

}
