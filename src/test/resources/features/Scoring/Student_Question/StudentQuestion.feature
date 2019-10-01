Feature: Student Question

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare quiz request with batchCode "futur3"
    And user hit get all quiz endpoint
    And user get first quiz id and store id
    And user get first quiz and store id
    And user prepare student question request with batchCode "futur3"

  @Negative @StudentQuestion
  Scenario: User hit find all unanswered questions with logging in as admin
    When user prepare student quiz request with stored quiz id and batchCode "futur3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question error response code should be 403

  @Negative @StudentQuestion
  Scenario: User hit find all unanswered questions without logging in
    Given user hit logout endpoint
    When user prepare student quiz request with stored quiz id and batchCode "futur3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question error response code should be 401

  @Positive @StudentQuestion
  Scenario: User hit find all unanswered questions with logging in as appropriate student
    Given user hit logout endpoint
    When user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    And user prepare student quiz request with stored quiz id and batchCode "futur3"
    And user hit get or create student quiz endpoint
    And user hit find all unanswered questions
    Then student question response code should be 200
    And student question size should be more than 0
