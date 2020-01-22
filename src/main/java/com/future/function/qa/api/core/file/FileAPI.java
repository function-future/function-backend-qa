package com.future.function.qa.api.core.file;

import com.future.function.qa.api.BaseAPI;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

import java.io.File;
import java.util.Optional;
import java.util.function.Supplier;

import static com.future.function.qa.util.Path.FILE;

public class FileAPI extends BaseAPI {

  @Override
  public RequestSpecification prepare() {

    base = super.prepare()
      .basePath(FILE);

    return base;
  }

  @Step
  public Response create(
    String parentId, File file, String data, Cookie cookie
  ) {

    return Optional.ofNullable(file)
      .map(f -> this.createFile(parentId, f, data, cookie))
      .orElseGet(() -> this.createFolder(parentId, data, cookie));
  }

  private Response createFile(
    String parentId, File file, String data, Cookie cookie
  ) {

    return doByCookiePresent(cookie,
                             this.createFileWithCookie(parentId, file, data,
                                                       cookie
                             ),
                             this.createFileWithoutCookie(parentId, file, data)
    );
  }

  private Supplier<Response> createFileWithCookie(
    String parentId, File file, String data, Cookie cookie
  ) {

    return () -> base.multiPart("file", file)
      .multiPart("data", data)
      .cookie(cookie)
      .post(String.format(PATH_ID, parentId));
  }

  private Supplier<Response> createFileWithoutCookie(
    String parentId, File file, String data
  ) {

    return () -> base.multiPart("file", file)
      .multiPart("data", data)
      .post(String.format(PATH_ID, parentId));
  }

  private Response createFolder(String parentId, String data, Cookie cookie) {

    return doByCookiePresent(
      cookie, this.createFolderWithCookie(parentId, data, cookie),
      this.createFolderWithoutCookie(parentId, data)
    );
  }

  private Supplier<Response> createFolderWithCookie(
    String parentId, String data, Cookie cookie
  ) {

    return () -> base.multiPart("data", data)
      .cookie(cookie)
      .post(String.format(PATH_ID, parentId));
  }

  private Supplier<Response> createFolderWithoutCookie(
    String parentId, String data
  ) {

    return () -> base.multiPart("data", data)
      .post(String.format(PATH_ID, parentId));
  }

  @Step
  public Response get(String parentId, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.getFilesFoldersWithCookie(parentId, cookie),
                             this.getFilesFoldersWithoutCookie(parentId)
    );
  }

  private Supplier<Response> getFilesFoldersWithCookie(
    String parentId, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID, parentId));
  }

  private Supplier<Response> getFilesFoldersWithoutCookie(String parentId) {

    return () -> base.get(String.format(PATH_ID, parentId));
  }

  @Step
  public Response getFileDetail(String id, String parentId, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.getFileDetailWithCookie(id, parentId, cookie),
                             this.getFileDetailWithoutCookie(id, parentId)
    );
  }

  private Supplier<Response> getFileDetailWithCookie(
    String id, String parentId, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .get(String.format(PATH_ID,
                         String.join("", parentId, String.format(PATH_ID, id))
      ));
  }

  private Supplier<Response> getFileDetailWithoutCookie(
    String id, String parentId
  ) {

    return () -> base.get(String.format(PATH_ID, String.join("", parentId,
                                                             String.format(
                                                               PATH_ID, id)
    )));
  }

  @Step
  public Response update(
    String id, String parentId, File file, String data, Cookie cookie
  ) {

    return Optional.ofNullable(file)
      .map(f -> this.updateFile(id, parentId, f, data, cookie))
      .orElseGet(() -> this.updateFolder(id, parentId, data, cookie));
  }

  private Response updateFile(
    String id, String parentId, File file, String data, Cookie cookie
  ) {

    return doByCookiePresent(cookie,
                             this.updateFileWithCookie(id, parentId, file, data,
                                                       cookie
                             ), this.updateFileWithoutCookie(id, parentId, file,
                                                             data
      )
    );
  }

  private Supplier<Response> updateFileWithCookie(
    String id, String parentId, File file, String data, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .multiPart("file", file)
      .multiPart("data", data)
      .put(String.format(PATH_ID,
                         String.join("", parentId, String.format(PATH_ID, id))
      ));
  }

  private Supplier<Response> updateFileWithoutCookie(
    String id, String parentId, File file, String data
  ) {

    return () -> base.multiPart("file", file)
      .multiPart("data", data)
      .put(String.format(PATH_ID,
                         String.join("", parentId, String.format(PATH_ID, id))
      ));
  }

  private Response updateFolder(
    String id, String parentId, String data, Cookie cookie
  ) {

    return doByCookiePresent(cookie,
                             this.updateFolderWithCookie(id, parentId, data,
                                                         cookie
                             ),
                             this.updateFolderWithoutCookie(id, parentId, data)
    );
  }

  private Supplier<Response> updateFolderWithCookie(
    String id, String parentId, String data, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .multiPart("data", data)
      .put(String.format(PATH_ID,
                         String.join("", parentId, String.format(PATH_ID, id))
      ));
  }

  private Supplier<Response> updateFolderWithoutCookie(
    String id, String parentId, String data
  ) {

    return () -> base.multiPart("data", data)
      .put(String.format(PATH_ID,
                         String.join("", parentId, String.format(PATH_ID, id))
      ));
  }

  @Step
  public Response delete(String id, String parentId, Cookie cookie) {

    return doByCookiePresent(cookie,
                             this.deleteFileFolderWithCookie(id, parentId,
                                                             cookie
                             ), this.deleteFileFolderWithoutCookie(id, parentId)
    );
  }

  private Supplier<Response> deleteFileFolderWithCookie(
    String id, String parentId, Cookie cookie
  ) {

    return () -> base.cookie(cookie)
      .delete(String.format(PATH_ID, String.join("", parentId,
                                                 String.format(PATH_ID, id)
      )));
  }

  private Supplier<Response> deleteFileFolderWithoutCookie(
    String id, String parentId
  ) {

    return () -> base.delete(String.format(PATH_ID, String.join("", parentId,
                                                                String.format(
                                                                  PATH_ID, id)
    )));
  }

}
