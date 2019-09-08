package com.future.function.qa.data.core.resource;

import org.springframework.stereotype.Component;

import com.future.function.qa.data.BaseData;
import com.future.function.qa.model.response.base.DataResponse;
import com.future.function.qa.model.response.core.resource.FileContentWebResponse;
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
public class ResourceData extends BaseData {

  private DataResponse<FileContentWebResponse> createdResponse = new DataResponse<>();
}
