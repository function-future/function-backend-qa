package com.future.function.qa.api.core.user;

import static com.future.function.qa.util.Path.USER;

import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.user.UserWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class UserAPI extends BaseAPI {

  private static final String SEARCH = "/_search";

  @Step
  public Response createUser(UserWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, createUserWithCookie(request, cookie), createUserWithoutCookie(request));
  }

  private Supplier<Response> createUserWithCookie(UserWebRequest request, Cookie cookie) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .cookie(cookie)
        .post();
  }

  private Supplier<Response> createUserWithoutCookie(UserWebRequest request) {

    return () -> base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response getUser(String id, Cookie cookie) {

    return doByCookiePresent(cookie, getUserWithCookie(id, cookie), getUserWithoutCookie(id));
  }

  private Supplier<Response> getUserWithCookie(String id, Cookie cookie) {

    return () -> base.cookie(cookie)
        .get(String.format(PATH_ID, id));
  }

  private Supplier<Response> getUserWithoutCookie(String id) {

    return () -> base.get(String.format(PATH_ID, id));
  }

  @Step
  public Response getUsers(String role, int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getUsersWithCookie(role, page, size, cookie),
        getUsersWithoutCookie(role, page, size));
  }

  private Supplier<Response> getUsersWithCookie(String role, int page, int size, Cookie cookie) {

    return () -> base.cookie(cookie)
        .queryParam("role", role)
        .queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  private Supplier<Response> getUsersWithoutCookie(String role, int page, int size) {

    return () -> base.queryParam("role", role)
        .queryParam("page", page)
        .queryParam("size", size)
        .get();
  }

  @Step
  public Response getUsersByName(String namePart, int page, int size, Cookie cookie) {

    return doByCookiePresent(cookie, getUsersByNameWithCookie(namePart, page, size, cookie),
        getUsersByNameWithoutCookie(namePart, page, size));
  }

  private Supplier<Response> getUsersByNameWithCookie(String namePart, int page, int size, Cookie cookie) {

    return () -> base.cookie(cookie)
        .queryParam("name", namePart)
        .queryParam("page", page)
        .queryParam("size", size)
        .get(SEARCH);
  }

  private Supplier<Response> getUsersByNameWithoutCookie(String namePart, int page, int size) {

    return () -> base.queryParam("name", namePart)
        .queryParam("page", page)
        .queryParam("size", size)
        .get(SEARCH);
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(USER);

    return base;
  }
}
