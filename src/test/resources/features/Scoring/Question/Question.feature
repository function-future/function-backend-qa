@Question @Regression
Feature: Question in Question Bank

  Background:
    Given user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare question bank request
    And user hit get all question banks endpoint
    And get first question bank data id
    And user prepare question request

  @Negative @Question
  Scenario: Create question without logging in
    Given user hit logout endpoint
    When user hit create question endpoint with label "Question Label", options, and id from created question bank
    Then question error response code should be 401

  @Negative @Question
  Scenario: Create question with empty label and less than 4 options
    Given user hit create question endpoint with label "", empty options, and id from created question bank
    Then question error response code should be 400
    And question error response body with key "label" should be with value "NotBlank"
    And question error response body with key "options" should be with value "Size"

  @Positive @Question
  Scenario: Create question with logging in as admin
    Given user hit create question endpoint with label "Question Label", options, and id from created question bank
    Then question response code should be 201
    And question response body label should be "Question Label"
    And question response body options size should be inputted options size
    And question response body id should not be null

  @Negative @Question
  Scenario: Get all question without logging in
    Given user hit logout endpoint
    When user hit get all question endpoint
    Then question error response code should be 401

  @Positive @Question
  Scenario: Get all question with logging in as admin
    Given user hit get all question endpoint
    Then question paging response data size should not be zero
    And question paging response data should contains label "Question Label"
    And question paging response data should contains id from previous created question
    And question paging response paging object should not be null
