package com.future.function.qa.data.scoring.comment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.comment.CommentWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.comment.CommentWebResponse;
import io.restassured.response.Response;
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
public class CommentData extends BaseData {

  private DataResponse<CommentWebResponse> singleResponse;
  private PagingResponse<CommentWebResponse> pagedResponse;

  public CommentWebRequest createRequest(String comment) {

    return CommentWebRequest.builder()
        .comment(comment)
        .build();
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<CommentWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<CommentWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);
  }
}
