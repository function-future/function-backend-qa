package com.future.function.qa.data.scoring.report_detail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.report_detail.ReportDetailScoreWebRequest;
import com.future.function.qa.model.request.scoring.report_detail.ScoreStudentWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.scoring.report_detail.ReportDetailWebResponse;
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
public class ReportDetailData extends BaseData {

  private DataResponse<ReportDetailWebResponse> singleResponse;

  private String reportId;

  public ReportDetailScoreWebRequest createRequest(List<ScoreStudentWebRequest> requests) {

    return ReportDetailScoreWebRequest.builder()
        .scores(requests)
        .build();
  }

  public void setResponse(Response responses) {
    super.setResponse(responses);
    this.singleResponse = Optional.of(responses)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<ReportDetailWebResponse>>() {
        }))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(null);

  }

}
