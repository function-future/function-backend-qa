package com.future.function.qa.api.core.shared_course;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.shared_course.DiscussionWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.DISCUSSION;

public class DiscussionAPI extends BaseAPI {

  public void prepare(String batchCode, String courseId) {

    base = super.prepare()
      .basePath(String.format(DISCUSSION, batchCode, courseId));
  }

  @Step
  public Response create(DiscussionWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.createDiscussionWithCookie(request, cookie),
                             this.createDiscussionWithoutCookie(request)
    );
  }

  private Supplier<Response> createDiscussionWithCookie(
    DiscussionWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  private Supplier<Response> createDiscussionWithoutCookie(
    DiscussionWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.getDiscussionsWithCookie(page, size, cookie),
                             this.getDiscussionsWithoutCookie(page, size)
    );
  }

  private Supplier<Response> getDiscussionsWithCookie(
    int page, int size, Cookie cookie
  ) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .cookie(cookie)
      .get();
  }

  private Supplier<Response> getDiscussionsWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

}
