package com.future.function.qa.data.core.sticky_note;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.sticky_note.StickyNoteWebRequest;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.base.PagingResponse;
import com.future.function.qa.model.response.core.sticky_note.StickyNoteWebResponse;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StickyNoteData extends BaseData {

  private DataResponse<StickyNoteWebResponse> createdResponse = new DataResponse<>();

  private StickyNoteWebRequest request;

  private PagingResponse<StickyNoteWebResponse> retrievedResponse = new PagingResponse<>();

  public StickyNoteWebRequest createRequest(String title, String description) {

    request = StickyNoteWebRequest.builder()
        .title(title)
        .description(description)
        .build();

    return request;
  }

  @Override
  public void setResponse(Response response) {

    super.setResponse(response);
    this.createdResponse = asDataResponse(response, new TypeReference<DataResponse<StickyNoteWebResponse>>() {});
    this.retrievedResponse = asPagingResponse(response, new TypeReference<PagingResponse<StickyNoteWebResponse>>() {});
  }
}
