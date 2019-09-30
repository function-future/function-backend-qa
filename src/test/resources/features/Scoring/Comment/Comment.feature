Feature: Comment Feature

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare assignment request with batchCode "futur3"
    And user hit get all assignment endpoint
    And user get first assignment id and store id
    And user prepare user request
    And user hit get users endpoint with role "STUDENT", page 1, size 10
    And user get first student id and store id
    And user prepare comment request

  @Negative @Comment
  Scenario: User create comment with logging in as admin
    When user hit create comment endpoint with comment "Comment 1"
    Then comment error response code should be 403

  @Negative @Comment
  Scenario: User create comment without logging in
    Given user hit logout endpoint
    When user hit create comment endpoint with comment "Comment 1"
    Then comment error response code should be 401

  @Positive @Comment
  Scenario: User create comment with logging in as mentor
    Given user hit logout endpoint
    And user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
    When user hit create comment endpoint with comment "Comment 1"
    Then comment response code should be 201
    And comment response body comment should be "Comment 1"
    And comment response body author name should be "Oliver"

  @Negative @Comment
  Scenario: User get all comment without logging in
    Given user hit logout endpoint
    And user hit get all comment endpoint
    Then comment error response code should be 401

  @Positive @Comment
  Scenario: User get all comment with logging in as admin
    When user hit get all comment endpoint
    Then comment paging response code should be 200
    And comment paging response body should contains comment "Comment 1" and author name "Oliver"
