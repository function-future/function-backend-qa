package com.future.function.qa.data.scoring.room;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.scoring.room.RoomWebResponse;
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
public class RoomData extends BaseData {

  private DataResponse<RoomWebResponse> singleResponse;

  private PagingResponse<RoomWebResponse> pagedResponse;

  private String studentId;

  private String assignmentId;

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagedResponse = Optional.of(response)
        .map(res -> asPagingResponse(res, new TypeReference<PagingResponse<RoomWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.pagedResponse);
    this.singleResponse = Optional.of(response)
        .map(res -> asDataResponse(res, new TypeReference<DataResponse<RoomWebResponse>>() {}))
        .filter(res -> Objects.nonNull(res.getData()))
        .orElse(this.singleResponse);

  }

}
