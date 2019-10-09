package com.future.function.qa.data.scoring.report_detail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.report_detail.ReportDetailScoreWebRequest;
import com.future.function.qa.model.request.scoring.report_detail.ScoreStudentWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.scoring.report_detail.ReportDetailWebResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
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

  private DataResponse<List<ReportDetailWebResponse>> listedResponse;

  private String reportId;

  public ReportDetailScoreWebRequest createRequest(List<ScoreStudentWebRequest> requests) {

    return ReportDetailScoreWebRequest.builder()
        .scores(requests)
        .build();
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.listedResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<List<ReportDetailWebResponse>>>() {}))
        .filter(res -> Objects.nonNull(res.getData()) && res.getData().size() > 0)
        .orElse(this.listedResponse);

  }

}
