package com.future.function.qa.data.scoring.report;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.report.ReportWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.user.UserWebResponse;
import com.future.function.qa.model.response.scoring.report.ReportWebResponse;
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
public class ReportData extends BaseData {

  private PagingResponse<UserWebResponse> studentPagingResponse;
  private DataResponse<ReportWebResponse> singleResponse;
  private PagingResponse<ReportWebResponse> pagedResponse;

  public ReportWebRequest createRequest(String name, String description, List<String> studentIds) {

    return ReportWebRequest.builder()
        .name(name)
        .description(description)
        .students(studentIds)
        .build();
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<ReportWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<ReportWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);
    this.studentPagingResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<UserWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.studentPagingResponse);

  }

}
