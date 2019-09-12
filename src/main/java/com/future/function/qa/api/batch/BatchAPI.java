package com.future.function.qa.api.batch;

import static com.future.function.qa.util.Path.BATCH;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.batch.BatchWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class BatchAPI extends BaseAPI {

  @Step
  public Response create(BatchWebRequest request, Cookie cookie) {

    if (cookie == null) {
      return createBatchWithoutCookie(request);
    }

    return createBatchWithCookie(request, cookie);
  }

  private Response createBatchWithCookie(BatchWebRequest request, Cookie cookie) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Response createBatchWithoutCookie(BatchWebRequest request) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(BATCH);

    return base;
  }
}
