package com.future.function.qa.data.core.activity_blog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.activity_blog.ActivityBlogWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.activity_blog.ActivityBlogWebResponse;
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
public class ActivityBlogData extends BaseData {

  private ActivityBlogWebRequest request;

  private DataResponse<ActivityBlogWebResponse> createdResponse =
    new DataResponse<>();

  private DataResponse<ActivityBlogWebResponse> retrievedResponse =
    new DataResponse<>();

  private PagingResponse<ActivityBlogWebResponse> pagingResponse =
    new PagingResponse<>();

  public void addRequestFiles(String resourceId) {

    if (request == null) {

      request = ActivityBlogWebRequest.builder()
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

  public ActivityBlogWebRequest createRequest(
    String title, String description
  ) {

    request = ActivityBlogWebRequest.builder()
      .title(title)
      .description(description)
      .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(
      response, new TypeReference<DataResponse<ActivityBlogWebResponse>>() {});
    this.retrievedResponse = asDataResponse(
      response, new TypeReference<DataResponse<ActivityBlogWebResponse>>() {});
    this.pagingResponse = asPagingResponse(response,
                                           new TypeReference<PagingResponse<ActivityBlogWebResponse>>() {}
    );
  }

}
