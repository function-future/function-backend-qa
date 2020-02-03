package com.future.function.qa.data.communication.reminder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.communication.reminder.ReminderWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.communication.reminder.ReminderDetailResponse;
import com.future.function.qa.model.response.communication.reminder.ReminderWebResponse;
import io.restassured.response.Response;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReminderData extends BaseData {

  private PagingResponse<ReminderWebResponse> pagingResponse = new PagingResponse<>();

  private DataResponse<ReminderDetailResponse> detailResponse = new DataResponse<>();

  public ReminderWebRequest createReminderRequest(
          String title, String description, Boolean isRepeatedMonthly,
          Integer monthlyDate, List<String> repeatDays, Integer hour,
          Integer minute, List<String> members) {
    return ReminderWebRequest.builder()
            .description(description)
            .title(title)
            .isRepeatedMonthly(isRepeatedMonthly)
            .monthlyDate(monthlyDate)
            .repeatDays(repeatDays)
            .hour(hour)
            .minute(minute)
            .members(members)
            .build();
  }

  public void setDetailResponse(Response response) {
    this.setResponseCode(response.getStatusCode());
    this.detailResponse = asDataResponse(response, new TypeReference<DataResponse<ReminderDetailResponse>>() {});
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<ReminderWebResponse>>() {});
  }
}
