@Question @Scoring @Regression
Feature: Question in Question Bank Feature

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
    And user hit logout endpoint

  @Positive @Question
  Scenario: Create question with logging in as admin
    Given user hit create question endpoint with label "Question Label", options, and id from created question bank
    Then question response code should be 201
    And question response body label should be "Question Label"
    And question response body options size should be inputted options size
    And question response body id should not be null
    And user hit logout endpoint

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
    And question paging response paging object should not be null
    And user hit logout endpoint

  @Negative @Question
  Scenario: Get question without logging in
    Given user hit logout endpoint
    When user hit get question endpoint
    Then question error response code should be 401

  @Negative @Question
  Scenario: Get question by random id
    When user hit get question endpoint with random id
    Then question error response code should be 404
    And question error response status should be "NOT_FOUND"
    And user hit logout endpoint

  @Positive @Question
  Scenario: Get question by previous created id
    When user hit get question endpoint
    Then question response body label should be "Question Label"
    And question response body id should not be null
    And user hit logout endpoint

  @Negative @Question
  Scenario: Update question without logging in
    Given user hit logout endpoint
    When user hit update question endpoint with persisted id, label "Question Label 2", and options label prefix "PREFIX-"
    Then question error response code should be 401

  @Negative @Question
  Scenario: Update question with empty attributes
    When user hit update question endpoint with persisted id, label "", and options label prefix ""
    Then question error response code should be 400
    And question error response body with key "label" should be with value "NotBlank"
    And user hit logout endpoint

  @Positive @Question
  Scenario: Update question with logging in as admin
    When user hit update question endpoint with persisted id, label "Question Label 2", and options label prefix "PREFIX-"
    Then question response code should be 200
    And question response body label should be "Question Label 2"
    And question response body options should contains prefix "PREFIX-"
    And user hit logout endpoint

  @Negative @Question
  Scenario: Delete question without logging in
    Given user hit logout endpoint
    When user hit delete question endpoint with persisted id
    Then question error response code should be 401

  @Positive @Question
  Scenario: Delete question with random id
    When user hit delete question endpoint with "id"
    Then question base response code should be 200
    And question base response status should be "OK"
    And user hit logout endpoint

  @Positive @Question
  Scenario: Delete question with logging as admin
    When user hit delete question endpoint with persisted id
    Then question base response code should be 200
    And question base response status should be "OK"
    When user hit get question endpoint
    Then question error response code should be 404
    And question error response status should be "NOT_FOUND"
    And user hit logout endpoint
