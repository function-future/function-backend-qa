package com.future.function.qa.util;

public interface Path {

  String ACTIVITY_BLOG = "/api/core/activity-blogs";

  String ANNOUNCEMENT = "/api/core/announcements";

  String AUTH = "/api/core/auth";

  String BATCH = "/api/core/batches";

  String COURSE = "/api/core/courses";

  String FILE = "/api/core/files";

  String RESOURCE = "/api/core/resources";

  String STICKY_NOTE = "/api/core/sticky-notes";

  String USER = "/api/core/users";

  String USER_DETAIL = "/api/core/user";

  String CHANGE_PASSWORD = USER_DETAIL + "/password";

  String PROFILE = USER_DETAIL + "/profile";

  String CHANGE_PROFILE_PICTURE = PROFILE + "/picture";

  String SHARED_COURSE = "/api/core/batches/%s/courses";

  String DISCUSSION = "/api/core/batches/%s/courses/%s/discussions";

  String QUESTION_BANK = "/api/scoring/question-banks";

  String QUESTION_BANK_QUESTION = QUESTION_BANK + "/%s/" + "questions";

  String QUIZ = "/api/scoring/batches/%s/quizzes";

  String COPY = "/copy";

  String ASSIGNMENT = "/api/scoring/batches/%s/assignments";

  String ROOM = "/api/scoring/batches/%s/assignments/%s/room/%s";

  String COMMENT = "/api/scoring/batches/%s/assignments/%s/room/%s/comments";

  String STUDENT_QUIZ = "/api/scoring/batches/%s/quizzes/%s/student";

  String STUDENT_QUESTION = "/api/scoring/batches/%s/quizzes/%s/student/questions";

  String REPORT = "/api/scoring/batches/%s/judgings";

  String STUDENTS = "/students";

  String CHATROOMS = "/api/communication/chatrooms";

  String LOGGINGROOM = "/api/communication/logging-rooms";

  String NOTIFICATIONS = "/api/communication/notifications";

  String REMINDERS = "/api/communication/reminders";

  String REPORT_DETAIL = "/api/scoring/batches/%s/judgings/%s/comparisons";

  String SUMMARY = "/api/scoring/summary/%s";

  String SUMMARY_WITHOUT_ID = "/api/scoring/summary";
}
