package com.future.function.qa.data.core.shared_course;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.shared_course.SharedCourseWebRequest;
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
import java.util.List;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SharedCourseData extends BaseData {

  private DataResponse<List<CourseWebResponse>> createdResponse =
    new DataResponse<>();

  private DataResponse<List<CourseWebResponse>> retrievedResponse =
    new DataResponse<>();

  private PagingResponse<CourseWebResponse> pagingResponse =
    new PagingResponse<>();

  private SharedCourseWebRequest request;

  public SharedCourseWebRequest createRequest(String originBatch) {

    request = SharedCourseWebRequest.builder()
      .courses(new ArrayList<>())
      .originBatch(originBatch)
      .build();

    return request;
  }

  public void addCourseIdToRequest(String courseId) {

    if (request.getCourses() == null) {
      request.setCourses(new ArrayList<>());
    }

    request.getCourses()
      .add(courseId);
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(
      response, new TypeReference<DataResponse<List<CourseWebResponse>>>() {});
    this.retrievedResponse = asDataResponse(
      response, new TypeReference<DataResponse<List<CourseWebResponse>>>() {});
    this.pagingResponse = asPagingResponse(
      response, new TypeReference<PagingResponse<CourseWebResponse>>() {});
  }

}
