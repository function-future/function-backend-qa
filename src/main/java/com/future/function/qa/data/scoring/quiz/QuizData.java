package com.future.function.qa.data.scoring.quiz;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.quiz.QuizWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.quiz.QuizWebResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class QuizData extends BaseData {

  private QuizWebRequest request;
  private PagingResponse<QuizWebResponse> pagedResponse;
  private DataResponse<QuizWebResponse> singleResponse;

  public QuizWebRequest createRequest(String title, String description, int timeLimit, int trials,
      long startDate, long endDate, int questionCount, List<String> questionBanks) {

    return QuizWebRequest.builder()
        .title(title)
        .description(description)
        .timeLimit(timeLimit)
        .trials(trials)
        .startDate(startDate)
        .endDate(endDate)
        .questionCount(questionCount)
        .questionBanks(questionBanks)
        .build();
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<QuizWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<QuizWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);
  }

}
