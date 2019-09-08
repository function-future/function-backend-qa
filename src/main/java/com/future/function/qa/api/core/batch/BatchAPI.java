package com.future.function.qa.api.core.batch;

import static com.future.function.qa.util.Path.BATCH;

import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.batch.BatchWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class BatchAPI extends BaseAPI {

  @Step
  public Response create(BatchWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createBatchWithCookie(request, cookie), createBatchWithoutCookie(request));
  }

  private Supplier<Response> createBatchWithCookie(BatchWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createBatchWithoutCookie(BatchWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response delete(String id, Cookie cookie) {

    return doByCookiePresent(cookie, deleteBatchWithCookie(id, cookie), deleteBatchWithoutCookie(id));
  }

  private Supplier<Response> deleteBatchWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
        .delete(String.format(PATH_ID, id));
  }

  private Supplier<Response> deleteBatchWithoutCookie(String id) {

    return () -> base.delete(String.format(PATH_ID, id));
  }

  @Step
  public Response edit(BatchWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, editBatchWithCookie(request, cookie), editBatchWithoutCookie(request));
  }

  private Supplier<Response> editBatchWithCookie(BatchWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .put(String.format(PATH_ID, request.getId()));
  }

  private Supplier<Response> editBatchWithoutCookie(BatchWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .put(String.format(PATH_ID, request.getId()));
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getWithCookie(page, size, cookie), getWithoutCookie(page, size));
  }

  private Supplier<Response> getWithCookie(int page, int size, Cookie cookie) {

    return () -> base.cookie(cookie)
        .queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  private Supplier<Response> getWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    return doByCookiePresent(cookie, getDetailWithCookie(id, cookie), getDetailWithoutCookie(id));
  }

  private Supplier<Response> getDetailWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getDetailWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(BATCH);

    return base;
  }
}
