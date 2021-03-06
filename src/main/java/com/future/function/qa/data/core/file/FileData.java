package com.future.function.qa.data.core.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.file.FileWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.file.DataPageResponse;
import com.future.function.qa.model.response.core.file.FileWebResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileData extends BaseData {

  private FileWebRequest request;

  private File file;

  private DataResponse<FileWebResponse<FileContentWebResponse>>
    createdResponse = new DataResponse<>();

  private DataResponse<FileWebResponse<FileContentWebResponse>>
    retrievedResponse = new DataResponse<>();

  private DataPageResponse<FileWebResponse<List<FileContentWebResponse>>>
    retrievedResponses = new DataPageResponse<>();

  public FileWebRequest createRequest(String pathOrName, String type) {

    this.request = null;
    this.file = new File(pathOrName);

    if (!this.file.exists()) {
      this.file = null;
    }

    request = FileWebRequest.builder()
      .type(type)
      .build();

    if (type.equals("FILE")) {
      request.setName(file.getName());
    } else {
      request.setName(pathOrName);
    }

    return request;
  }

  public String getRequestAsJson() {

    try {
      return mapper.writeValueAsString(this.request);
    } catch (Exception e) {
      e.printStackTrace();
      return "{}";
    }
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(
      response,
      new TypeReference<DataResponse<FileWebResponse<FileContentWebResponse>>>() {}
    );
    this.retrievedResponse = asDataResponse(
      response,
      new TypeReference<DataResponse<FileWebResponse<FileContentWebResponse>>>() {}
    );
    this.retrievedResponses = asDataPageResponse(response);
  }

  private <T> DataPageResponse<T> asDataPageResponse(Response response) {

    try {
      return mapper.readValue(
        response.print(),
        new TypeReference<DataPageResponse<FileWebResponse<List<FileContentWebResponse>>>>() {}
      );
    } catch (Exception e) {
      return new DataPageResponse<>();
    }
  }

}
