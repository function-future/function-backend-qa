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
  public Response edit(BatchWebRequest request, Cookie cookie) {

    if (cookie == null) {
      return editBatchWithoutCookie(request);
    }

    return editBatchWithCookie(request, cookie);
  }

  private Response editBatchWithCookie(BatchWebRequest request, Cookie cookie) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .put(String.format(PATH_ID, request.getId()));
  }

  private Response editBatchWithoutCookie(BatchWebRequest request) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .put(String.format(PATH_ID, request.getId()));
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    if (cookie == null) {
      return getWithoutCookie(page, size);
    }

    return getWithCookie(page, size, cookie);
  }

  private Response getWithCookie(int page, int size, Cookie cookie) {

    return base.cookie(cookie)
        .queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  private Response getWithoutCookie(int page, int size) {

    return base.queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    if (cookie == null) {
      return getDetailWithoutCookie(id);
    }

    return getDetailWithCookie(id, cookie);
  }

  private Response getDetailWithCookie(String id, Cookie cookie) {

    return base.cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Response getDetailWithoutCookie(String id) {

    return base.get(String.format(PATH_ID, id));
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(BATCH);

    return base;
  }
}
