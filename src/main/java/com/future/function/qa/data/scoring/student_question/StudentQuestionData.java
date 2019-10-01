package com.future.function.qa.data.scoring.student_question;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.student_question.StudentQuestionWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.student_question.StudentQuestionWebResponse;
import com.future.function.qa.model.response.scoring.student_question.StudentQuizDetailWebResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class StudentQuestionData extends BaseData {

  private String batchCode;
  private String quizId;

  private PagingResponse<StudentQuestionWebResponse> pagedResponse;

  private DataResponse<StudentQuizDetailWebResponse> quizDetailResponse;

  public List<StudentQuestionWebRequest> createRequestList(Map<Integer, String> answers) {
    List<StudentQuestionWebRequest> requestList = new ArrayList<>();
    answers.forEach((key, value) -> requestList.add(new StudentQuestionWebRequest(key, value)));
    return requestList;
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<StudentQuestionWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.quizDetailResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<StudentQuizDetailWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.quizDetailResponse);

  }

}
