Feature: Assignment

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare assignment request with batchCode "futur3"

  @Negative @Assignment
  Scenario: user create assignment without logging in
    Given user hit logout endpoint
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1500000, and empty list of files
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: user create assignment with empty title and description
    When user hit create assignment endpoint with title "", description "", deadline 1500000, and empty list of files
    Then assignment error response code should be 400
    And assignment error response body should have key "title" and value "NotBlank"
    And assignment error response body should have key "description" and value "NotBlank"

  @Positive @Assignment
  Scenario: user create assignment with logging in as admin
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1500000, and empty list of files
    Then assignment response code should be 201
    And assignment response body title should be "Assignment Title"
    And assignment response body description should be "Assignment Description"
    And assignment response body deadline should be 1500000

  @Negative @Assignment
  Scenario: user get all assignment without logging in
    Given user hit logout endpoint
    When user hit get all assignment endpoint
    Then assignment error response code should be 401

  @Positive @Assignment
  Scenario: user get all assignment with logging in as admin
    When user hit get all assignment endpoint
    Then assignment paging response code should be 200
    And assignment paging response body should contains title "Assignment Title" and description "Assignment Description"

  @Negative @Assignment
  Scenario: user get assignment by previous created id and without logging in
    Given user hit logout endpoint
    When user hit get assignment endpoint with previous created id
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: user get assignment with random id
    When user get assignment endpoint with random id
    Then assignment error response code should be 404
    And assignment error response status should be "NOT_FOUND"

  @Positive @Assignment
  Scenario: user get assignment by previous created id and logging in as admin
    When user hit get assignment endpoint with previous created id
    Then assignment response code should be 200
    And assignment response body title should be "Assignment Title"
    And assignment response body description should be "Assignment Description"
    And assignment response body deadline should be 1500000
