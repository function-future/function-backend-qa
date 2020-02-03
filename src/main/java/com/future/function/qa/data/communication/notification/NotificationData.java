package com.future.function.qa.data.communication.notification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.communication.notification.NotificationWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.communication.notification.NotificationWebResponse;
import com.future.function.qa.model.response.communication.notification.UnseenNotificationWebResponse;
import io.restassured.response.Response;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotificationData extends BaseData {

  private PagingResponse<NotificationWebResponse> pagingResponse = new PagingResponse<>();

  private DataResponse<NotificationWebResponse> dataResponse = new DataResponse<>();

  private DataResponse<UnseenNotificationWebResponse> unseenResponse = new DataResponse<>();

  public NotificationWebRequest createNotificationRequest(String description, String targetUser, String title) {
    return NotificationWebRequest.builder()
            .description(description)
            .targetUser(targetUser)
            .title(title)
            .build();
  }

  public void setUnseenResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.unseenResponse = asDataResponse(response, new TypeReference<DataResponse<UnseenNotificationWebResponse>>() {});
  }

  public void setDataResponse(Response response) {
    this.responseCode = response.getStatusCode();
    this.dataResponse = asDataResponse(response, new TypeReference<DataResponse<NotificationWebResponse>>() {});
  }

  @Override
  public void setResponse(Response response) {
    super.setResponse(response);
    this.pagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<NotificationWebResponse>>() {});
  }

}
