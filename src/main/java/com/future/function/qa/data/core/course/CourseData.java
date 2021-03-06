package com.future.function.qa.data.core.course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.course.CourseWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.course.CourseWebResponse;
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
public class CourseData extends BaseData {

  private CourseWebRequest request;

  private DataResponse<CourseWebResponse> createdResponse =
    new DataResponse<>();

  private DataResponse<CourseWebResponse> retrievedResponse =
    new DataResponse<>();

  private PagingResponse<CourseWebResponse> pagingResponse =
    new PagingResponse<>();

  public void addRequestMaterials(String resourceId) {

    if (request == null) {

      request = CourseWebRequest.builder()
        .material(Arrays.asList(resourceId))
        .build();

      return;
    }

    if (request.getMaterial() == null) {
      request.setMaterial(new ArrayList<>());
    }

    request.getMaterial()
      .add(resourceId);
  }

  public CourseWebRequest createRequest(String title, String description) {

    request = CourseWebRequest.builder()
      .title(title)
      .description(description)
      .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(
      response, new TypeReference<DataResponse<CourseWebResponse>>() {});
    this.retrievedResponse = asDataResponse(
      response, new TypeReference<DataResponse<CourseWebResponse>>() {});
    this.pagingResponse = asPagingResponse(
      response, new TypeReference<PagingResponse<CourseWebResponse>>() {});
  }

}
