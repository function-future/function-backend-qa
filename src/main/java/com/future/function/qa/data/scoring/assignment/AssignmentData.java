package com.future.function.qa.data.scoring.assignment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.assignment.AssignmentWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.assignment.AssignmentWebResponse;
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
public class AssignmentData extends BaseData {

  private PagingResponse<AssignmentWebResponse> pagedResponse;
  private DataResponse<AssignmentWebResponse> singleResponse;

  public AssignmentWebRequest createRequest(String title, String description, Long deadline, List<String> files) {

    return AssignmentWebRequest
        .builder()
        .title(title)
        .description(description)
        .deadline(deadline)
        .files(files)
        .build();
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<AssignmentWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<AssignmentWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);
  }

}
