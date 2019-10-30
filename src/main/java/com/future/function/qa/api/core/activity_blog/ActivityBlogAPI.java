package com.future.function.qa.api.core.activity_blog;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.activity_blog.ActivityBlogWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.ACTIVITY_BLOG;

public class ActivityBlogAPI extends BaseAPI {

  @Step
  public Response create(ActivityBlogWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             createActivityBlogWithCookie(request, cookie),
                             createActivityBlogWithoutCookie(request)
    );
  }

  private Supplier<Response> createActivityBlogWithCookie(
    ActivityBlogWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  private Supplier<Response> createActivityBlogWithoutCookie(
    ActivityBlogWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie,
                             getActivityBlogsWithCookie(page, size, cookie),
                             getActivityBlogsWithoutCookie(page, size)
    );
  }

  private Supplier<Response> getActivityBlogsWithCookie(
    int page, int size, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  private Supplier<Response> getActivityBlogsWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    return doByCookiePresent(cookie,
                             getActivityBlogDetailWithCookie(id, cookie),
                             getActivityBlogDetailWithoutCookie(id)
    );
  }

  private Supplier<Response> getActivityBlogDetailWithCookie(
    String id, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getActivityBlogDetailWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(ACTIVITY_BLOG);

    return base;
  }

  @Step
  public Response update(
    String id, ActivityBlogWebRequest request, Cookie cookie
  ) {

    return doByCookiePresent(cookie,
                             updateActivityBlogWithCookie(id, request, cookie),
                             updateActivityBlogWithoutCookie(id, request)
    );
  }

  private Supplier<Response> updateActivityBlogWithCookie(
    String id, ActivityBlogWebRequest request, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .contentType(ContentType.JSON)
      .body(request)
      .put(String.format(PATH_ID, id));
  }

  private Supplier<Response> updateActivityBlogWithoutCookie(
    String id, ActivityBlogWebRequest request
  ) {

    return () -> base.contentType(ContentType.JSON)
      .body(request)
      .put(String.format(PATH_ID, id));
  }

  @Step
  public Response delete(String id, Cookie cookie) {

    return doByCookiePresent(cookie, deleteActivityBlogWithCookie(id, cookie),
                             deleteActivityBlogWithoutCookie(id)
    );
  }

  private Supplier<Response> deleteActivityBlogWithCookie(
    String id, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .delete(String.format(PATH_ID, id));
  }

  private Supplier<Response> deleteActivityBlogWithoutCookie(String id) {

    return () -> base.delete(String.format(PATH_ID, id));
  }

}
