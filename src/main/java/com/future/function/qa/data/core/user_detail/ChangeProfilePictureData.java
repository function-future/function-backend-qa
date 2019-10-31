package com.future.function.qa.data.core.user_detail;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.core.user_detail.ChangeProfilePictureWebRequest;
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
public class ChangeProfilePictureData extends BaseData {

  private ChangeProfilePictureWebRequest request;

  public ChangeProfilePictureWebRequest createRequest(List<String> avatars) {

    if (request == null) {
      request = new ChangeProfilePictureWebRequest();
    }

    if (request.getAvatar() == null) {
      request.setAvatar(new ArrayList<>());
    }

    request.getAvatar()
      .addAll(avatars);

    return request;
  }

}
