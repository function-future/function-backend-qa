package com.future.function.qa.data.question_bank;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.question_bank.QuestionBankWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.question_bank.QuestionBankWebResponse;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankData extends BaseData {

  private PagingResponse<QuestionBankWebResponse> pagedResponse = new PagingResponse<>();

  private QuestionBankWebRequest request;

  private DataResponse<QuestionBankWebResponse> singleResponse = new DataResponse<>();

  public QuestionBankWebRequest createRequest(String id, String title, String description) {

    request = QuestionBankWebRequest.builder()
        .title(title)
        .description(description)
        .build();

    if (id != null) {
      request.setId(id);
    }

    return request;
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = asPagingResponse(response, new TypeReference<PagingResponse<QuestionBankWebResponse>>() {});
    this.singleResponse = asDataResponse(response, new TypeReference<DataResponse<QuestionBankWebResponse>>() {});
  }
}