package com.future.function.qa.data.core.announcement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.announcement.AnnouncementWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.announcement.AnnouncementWebResponse;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnnouncementData extends BaseData {

  private DataResponse<AnnouncementWebResponse> createdResponse = new DataResponse<>();

  private PagingResponse<AnnouncementWebResponse> pagingResponse = new PagingResponse<>();

  private AnnouncementWebRequest request;

  private DataResponse<AnnouncementWebResponse> retrievedResponse = new DataResponse<>();

  public void addRequestFiles(String resourceId) {

    if (request == null) {

      request = AnnouncementWebRequest.builder()
          .files(Arrays.asList(resourceId))
          .build();

      return;
    }

    if (request.getFiles() == null) {
      request.setFiles(new ArrayList<>());
    }

    request.getFiles()
        .add(resourceId);
  }

  public AnnouncementWebRequest createRequest(String title, String summary, String description) {

    request = AnnouncementWebRequest.builder()
        .title(title)
        .summary(summary)
        .description(description)
        .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(response, new TypeReference<DataResponse<AnnouncementWebResponse>>() {
    });
    this.retrievedResponse = asDataResponse(response, new TypeReference<DataResponse<AnnouncementWebResponse>>() {
    });
    this.pagingResponse = asPagingResponse(response, new TypeReference<PagingResponse<AnnouncementWebResponse>>() {
    });
  }
}
