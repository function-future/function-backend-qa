package com.future.function.qa.util;

public interface Path {

  String AUTH = "/api/core/auth";

  String BATCH = "/api/core/batches";

  String RESOURCE = "/api/core/resources";

  String STICKY_NOTE = "/api/core/sticky-notes";

  String USER = "/api/core/users";

  String QUESTION_BANK = "/api/scoring/question-banks";

  String QUESTION = "/questions";

  String QUIZ = "/api/scoring/batches/%s/quizzes";

  String COPY = "/copy";

  String ASSIGNMENT = "/api/scoring/batches/%s/assignments";

  String ROOM = "/api/scoring/batches/batchId/assignments/%s/room/%s";

  String COMMENT = "/api/scoring/batches/batchId/assignments/%s/room/%s/comments";

  String STUDENT_QUIZ = "/api/scoring/batches/%s/quizzes/%s/student";

  String STUDENT_QUESTION = "/api/scoring/batches/%s/quizzes/%s/student/questions";
}
