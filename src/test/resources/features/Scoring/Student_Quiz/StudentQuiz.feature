@StudentQuiz @Scoring @Regression
Feature: Student Quiz Feature

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare quiz request with batchCode "future3"
    And user hit get all quiz endpoint
    And user get first quiz and store id
    And user prepare student quiz request with stored quiz id and batchCode "future3"

  @Negative @StudentQuiz
  Scenario: User hit get or create student quiz with logging in as admin
    When user hit get or create student quiz endpoint
    Then student quiz error response code should be 403
    And user hit logout endpoint

  @Negative @StudentQuiz
  Scenario: User hit get or create student quiz without logging in
    Given user hit logout endpoint
    When user hit get or create student quiz endpoint
    Then student quiz error response code should be 401

  @Positive @StudentQuiz
  Scenario: User hit get or create student quiz
    Given user hit logout endpoint
    And user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    When user hit get or create student quiz endpoint
    Then student quiz response code should be 200
    And student quiz response body quiz id should be the same as the stored quiz id
    And student quiz response body id should not be null
    And user hit logout endpoint
