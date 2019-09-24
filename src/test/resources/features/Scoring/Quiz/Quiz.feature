Feature: Quiz

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    Then user prepare quiz request with batchCode "futur3"
    And user prepare question bank request

  @Negative @Quiz
  Scenario: Create quiz without logging in
    Given user hit get all question banks endpoint
    And user hit logout endpoint
    And user hit create quiz endpoint with title "Quiz Title", description "Quiz Description", trials 3, timeLimit 300,endDate 150000000, startDate 150000000, questionCount 3, and question bank ids get from question bank data
    Then quiz error response code should be 401

  @Negative @Quiz
  Scenario: Create quiz with blank attributes
    When user hit get all question banks endpoint
    And user hit create quiz endpoint with title "", description "", trials 0, timeLimit 0,endDate 150000000, startDate 150000000, questionCount 0, and question bank ids get from question bank data
    Then quiz error response code should be 400
    And quiz error response code body should have key "title" and value "NotBlank"
    And quiz error response code body should have key "description" and value "NotBlank"
    And quiz error response code body should have key "timeLimit" and value "MinimalOnePositiveNumber"
    And quiz error response code body should have key "trials" and value "MinimalOnePositiveNumber"
    And quiz error response code body should have key "questionCount" and value "MinimalOnePositiveNumber"

  @Positive @Quiz
  Scenario: Create quiz with logging in as admin
    When user hit get all question banks endpoint
    And user hit create quiz endpoint with title "Quiz Title", description "Quiz Description", trials 3, timeLimit 300,endDate 150000000, startDate 150000000, questionCount 3, and question bank ids get from question bank data
    Then quiz response code should be 201
    And quiz response body should have title "Quiz Title"
    And quiz response body should have description "Quiz Description"
    And quiz response body should have trials 3
    And quiz response body should have all ids of question bank list

  @Negative @Quiz
  Scenario: Get all quiz without logging in
    Given user hit logout endpoint
    When user hit get all quiz endpoint
    Then quiz error response code should be 401

  @Positive @Quiz
  Scenario: Get all quiz with logging in as admin
    When user hit get all quiz endpoint
    Then quiz paging response code should be 200
    And quiz paging response body should contains title "Quiz Title" and description "Quiz Description"
