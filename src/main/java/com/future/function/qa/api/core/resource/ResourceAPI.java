package com.future.function.qa.api.core.resource;

import static com.future.function.qa.util.Path.RESOURCE;

import java.io.File;
import java.util.function.Supplier;

import com.future.function.qa.api.BaseAPI;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class ResourceAPI extends BaseAPI {

  @Step
  public Response get(String uri) {

    return base.get(uri);
  }

  @Step
  public Response post(String varName, File file, String origin, Cookie cookie) {

    return doByCookiePresent(cookie, postResourceWithCookie(varName, file, origin, cookie),
        postResourceWithoutCookie(varName, file, origin));
  }

  private Supplier<Response> postResourceWithoutCookie(String varName, File file, String origin) {

    return () -> base.multiPart(varName, file)
        .queryParam("origin", origin)
        .post();
  }

  private Supplier<Response> postResourceWithCookie(String varName, File file, String origin, Cookie cookie) {

    return () -> base.cookie(cookie)
        .multiPart(varName, file)
        .queryParam("origin", origin)
        .post();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(RESOURCE);

    return base;
  }
}
