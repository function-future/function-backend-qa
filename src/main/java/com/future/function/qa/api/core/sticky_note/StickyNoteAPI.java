package com.future.function.qa.api.core.sticky_note;

import static com.future.function.qa.util.Path.STICKY_NOTE;

import com.future.function.qa.api.BaseAPI;
import com.future.function.qa.model.request.core.sticky_note.StickyNoteWebRequest;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class StickyNoteAPI extends BaseAPI {

  @Step
  public Response createStickyNote(StickyNoteWebRequest request, Cookie cookie) {

    return doByCookiePresent(cookie, () -> createStickyNoteWithCookie(request, cookie),
        () -> createStickyNoteWithoutCookie(request));
  }

  private Response createStickyNoteWithCookie(StickyNoteWebRequest request, Cookie cookie) {

    return base.cookie(cookie)
        .body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  private Response createStickyNoteWithoutCookie(StickyNoteWebRequest request) {

    return base.body(request)
        .contentType(ContentType.JSON)
        .post();
  }

  @Step
  public Response getStickyNote() {

    return base.get();
  }

  @Step
  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
        .basePath(STICKY_NOTE);

    return base;
  }
}
