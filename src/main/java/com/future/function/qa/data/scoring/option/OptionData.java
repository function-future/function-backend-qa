package com.future.function.qa.data.scoring.option;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.request.scoring.option.OptionWebRequest;
import com.future.function.qa.model.response.scoring.option.OptionWebResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OptionData extends BaseData {

  private List<OptionWebRequest> requestList;

  private OptionWebRequest request;

  private List<OptionWebResponse> optionWebResponses;

  public List<OptionWebRequest> createRequest(String id, String label, boolean... correctness) {
    List<OptionWebRequest> requests = new ArrayList<>();
    for(int i = 1; i < 5; i++) {
      request = OptionWebRequest.builder()
          .id(id)
          .label(label + i)
          .correct(correctness[i - 1])
          .build();
      requests.add(request);
    }
    requestList = requests;
    return requests;
  }

  public void setResponse(List<OptionWebResponse> responses) {
    optionWebResponses = responses;
  }

}
