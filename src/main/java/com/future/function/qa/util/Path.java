package com.future.function.qa.util;

public interface Path {

  String ACTIVITY_BLOG = "/api/core/activity-blogs";

  String ANNOUNCEMENT = "/api/core/announcements";

  String AUTH = "/api/core/auth";

  String BATCH = "/api/core/batches";

  String COURSE = "/api/core/courses";

  String RESOURCE = "/api/core/resources";

  String STICKY_NOTE = "/api/core/sticky-notes";

  String USER = "/api/core/users";

  String USER_DETAIL = "/api/core/user";

  String CHANGE_PASSWORD = USER_DETAIL + "/password";

  String PROFILE = USER_DETAIL + "/profile";

  String CHANGE_PROFILE_PICTURE = PROFILE + "/picture";

  String SHARED_COURSE = "/api/core/batches/%s/courses";

  String DISCUSSION = "/api/core/batches/%s/courses/%s/discussions";

}
