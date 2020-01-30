package com.future.function.qa.model.request.core.user_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfilePictureWebRequest {

  private List<String> avatar;

}
