package com.future.function.qa.api.core.shared_course;

import com.future.function.qa.api.BaseAPI;

import static com.future.function.qa.util.Path.SHARED_COURSE;

public class SharedCourseAPI extends BaseAPI {

  public void prepare(String batchCode) {

    base = super.prepare()
      .basePath(String.format(SHARED_COURSE, batchCode));
  }

}
