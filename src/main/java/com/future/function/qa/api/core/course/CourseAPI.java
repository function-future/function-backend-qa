package com.future.function.qa.api.core.course;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.course.CourseWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.util.function.Supplier;

import static com.future.function.qa.util.Path.COURSE;

public class CourseAPI extends BaseAPI {

  @Step
  public Response create(CourseWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createCourseWithCookie(request, cookie),
                             createCourseWithoutCookie(request)
    );
  }

  private Supplier<Response> createCourseWithoutCookie(
    CourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .post();
  }

  private Supplier<Response> createCourseWithCookie(
    CourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .post();
  }

  @Step
  public Response get(int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getCoursesWithCookie(page, size, cookie),
                             getCoursesWithoutCookie(page, size)
    );
  }

  private Supplier<Response> getCoursesWithCookie(
    int page, int size, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  private Supplier<Response> getCoursesWithoutCookie(int page, int size) {

    return () -> base.queryParam("page", page)
      .queryParam("size", size)
      .get();
  }

  @Step
  public Response getDetail(String id, Cookie cookie) {

    return doByCookiePresent(cookie, getCourseDetailWithCookie(id, cookie),
                             getCourseDetailWithoutCookie(id)
    );
  }

  private Supplier<Response> getCourseDetailWithCookie(
    String id, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getCourseDetailWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Step
  public Response update(String id, CourseWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie,
                             updateCourseWithCookie(id, request, cookie),
                             updateCourseWithoutCookie(id, request)
    );
  }

  private Supplier<Response> updateCourseWithCookie(
    String id, CourseWebRequest request, Cookie cookie
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .cookie(cookie)
      .put(String.format(PATH_ID, id));
  }

  private Supplier<Response> updateCourseWithoutCookie(
    String id, CourseWebRequest request
  ) {

    return () -> base.body(request)
      .contentType(ContentType.JSON)
      .put(String.format(PATH_ID, id));
  }

  @Step
  public Response delete(String id, Cookie cookie) {

    return doByCookiePresent(cookie, deleteCourseWithCookie(id, cookie),
                             deleteCourseWithoutCookie(id)
    );
  }

  private Supplier<Response> deleteCourseWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
      .delete(String.format(PATH_ID, id));
  }

  private Supplier<Response> deleteCourseWithoutCookie(String id) {

    return () -> base.delete(String.format(PATH_ID, id));
  }

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(COURSE);

    return base;
  }

}
