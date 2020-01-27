@StudentQuestion @Regression
Feature: Student Question

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare quiz request with batchCode "future3"
    And user hit get all quiz endpoint
    And user get first quiz id and store id
    And user get first quiz and store id
    And user prepare student question request with batchCode "future3"

  @Negative @StudentQuestion
  Scenario: User hit find all unanswered questions with logging in as admin
    When user prepare student quiz request with stored quiz id and batchCode "future3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question error response code should be 403
    And user hit logout endpoint

  @Negative @StudentQuestion
  Scenario: User hit find all unanswered questions without logging in
    Given user hit logout endpoint
    When user prepare student quiz request with stored quiz id and batchCode "future3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question error response code should be 401

  @Positive @StudentQuestion
  Scenario: User hit find all unanswered questions with logging in as appropriate student
    Given user hit logout endpoint
    When user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    And user prepare student quiz request with stored quiz id and batchCode "future3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question response code should be 200
    And student question size should be more than 0
    And user hit logout endpoint

  @Negative @StudentQuestion
  Scenario: User hit post answers endpoint without logging in
    Given user hit logout endpoint
    When user hit post answers endpoint with null answers
    Then student question error response code should be 401

  @Negative @StudentQuestion
  Scenario: User hit post answers endpoint with passing list of null numbers and answers
    Given user hit logout endpoint
    When user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    And user prepare student quiz request with stored quiz id and batchCode "future3"
    And user hit get or create student quiz endpoint
    And user store the current trials from student quiz
    And user hit find all unanswered questions
    And user hit post answers endpoint with null answers
    Then student question error response code should be 400
    And student question error response body should contains these data :
      | number   | NotNull |
      | optionId | NotNull |
    And user hit logout endpoint


  @Positive @StudentQuestion
  Scenario: User answers the unanswered questions with logging in as appropriate student
    Given user hit logout endpoint
    When user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    And user prepare student quiz request with stored quiz id and batchCode "future3"
    And user hit get or create student quiz endpoint
    And user store the current trials from student quiz
    And user hit find all unanswered questions
    And user hit post answers endpoint with unanswered questions as "user"
    Then student quiz detail response code should be 201
    And student question response body trials should not be null
    And user hit logout endpoint
