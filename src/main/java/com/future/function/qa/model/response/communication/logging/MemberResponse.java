package com.future.function.qa.model.response.communication.logging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

  private String id;

  private String name;

  private String avatar;

  private String role;

  private String university;

  private String batchName;

}
