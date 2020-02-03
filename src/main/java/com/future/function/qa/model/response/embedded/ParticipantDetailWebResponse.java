package com.future.function.qa.model.response.embedded;

import com.future.function.qa.model.response.core.batch.BatchWebResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDetailWebResponse {

  private String id;

  private String name;

  private String avatar;

  private String university;

  private BatchWebResponse batch;

  private String type;

}