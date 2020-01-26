package com.future.function.qa.data.core.shared_course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.shared_course.DiscussionWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.shared_course.DiscussionWebResponse;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DiscussionData extends BaseData {

  private DiscussionWebRequest request;

  private DataResponse<DiscussionWebResponse> createdResponse =
    new DataResponse<>();

  private PagingResponse<DiscussionWebResponse> pagingResponse =
    new PagingResponse<>();

  public DiscussionWebRequest createRequest(String comment) {

    request = DiscussionWebRequest.builder()
      .comment(comment)
      .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(
      response, new TypeReference<DataResponse<DiscussionWebResponse>>() {});
    this.pagingResponse = asPagingResponse(
      response, new TypeReference<PagingResponse<DiscussionWebResponse>>() {});
  }

}
