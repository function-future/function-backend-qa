package com.future.function.qa.api.core.shared_course;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.course.CourseWebRequest;
import com.future.function.qa.model.request.core.shared_course.SharedCourseWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.SHARED_COURSE;

public class SharedCourseAPI extends BaseAPI {

  public void prepare(String batchCode) {

    base = super.prepare()
      .basePath(String.format(SHARED_COURSE, batchCode));
  }

  @Step
  public Response create(
    String targetBatchCode, SharedCourseWebRequest request, Cookie cookie
  ) {

    return doByCookiePresent(cookie,
                             this.createSharedCourseWithCookie(targetBatchCode,
                                                               request, cookie
                             ), this.createSharedCourseWithoutCookie(
        targetBatchCode, request)
    );
  }

  private Supplier<Response> createSharedCourseWithCookie(
    String targetBatchCode, SharedCourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  private Supplier<Response> createSharedCourseWithoutCookie(
    String targetBatchCode, SharedCourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, this.getSharedCoursesWithCookie(page, size,
                                                                     cookie
    ), this.getSharedCoursesWithoutCookie(page, size));
  }

  private Supplier<Response> getSharedCoursesWithCookie(
    int page, int size, Cookie cookie
  ) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .cookie(cookie)
      .get();
  }

  private Supplier<Response> getSharedCoursesWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.getSharedCourseDetailWithCookie(id, cookie),
                             this.getSharedCourseDetailWithoutCookie(id)
    );
  }

  private Supplier<Response> getSharedCourseDetailWithCookie(
    String id, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getSharedCourseDetailWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Step
  public Response update(String id, CourseWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.updateSharedCourseWithCookie(id, request,
                                                               cookie
                             ),
                             this.updateSharedCourseWithoutCookie(id, request)
    );
  }

  private Supplier<Response> updateSharedCourseWithCookie(
    String id, CourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .put(String.format(PATH_ID, id));
  }

  private Supplier<Response> updateSharedCourseWithoutCookie(
    String id, CourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID, id));
  }

  @Step
  public Response delete(String id, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.deleteSharedCourseWithCookie(id, cookie),
                             this.deleteSharedCourseWithoutCookie(id)
    );
  }

  private Supplier<Response> deleteSharedCourseWithCookie(
    String id, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .delete(String.format(PATH_ID, id));
  }

  private Supplier<Response> deleteSharedCourseWithoutCookie(String id) {

    return () -> base.delete(String.format(PATH_ID, id));
  }

}
