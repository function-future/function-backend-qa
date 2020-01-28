package com.future.function.qa.data.scoring.question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.option.OptionWebRequest;
import com.future.function.qa.model.request.scoring.question.QuestionWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.question.QuestionWebResponse;
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
public class QuestionData extends BaseData {

  private PagingResponse<QuestionWebResponse> pagedResponse = new PagingResponse<>();

  private QuestionWebRequest request;

  private String questionBankId;

  private DataResponse<QuestionWebResponse> singleResponse = new DataResponse<>();

  public QuestionWebRequest createRequest(String label, List<OptionWebRequest> options) {

    request = QuestionWebRequest.builder()
        .label(label)
        .options(options)
        .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<QuestionWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<QuestionWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);
  }

}
