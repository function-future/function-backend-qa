@QuestionBank @Regression
Feature: Question Bank

  Background:
    Given user prepare question bank request
    And user prepare auth request

  @Negative @QuestionBank
  Scenario: Create question bank without logging in
    When user hit logout endpoint
    And user hit create question bank endpoint with title "QA Title" and description "QA Description"
    Then question bank error response code should be 401

  @Negative @QuestionBank
  Scenario: Create question bank with empty title and description after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create question bank endpoint with title "" and description ""
    Then question bank error response code should be 400
    And question bank error response has key "title" and value "NotBlank"
    And question bank error response has key "description" and value "NotBlank"
    And user hit logout endpoint

  @Positive @QuestionBank
  Scenario: Create question bank after logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create question bank endpoint with title "QA Title 1" and description "QA Description 1"
    Then question bank response code should be 201
    And question bank response data has title "QA Title 1"
    And question bank response data has description "QA Description 1"
    And question bank response data has id that should not be blank or null
    And user hit logout endpoint

  @Negative @QuestionBank
  Scenario: Get question bank without logging in
    When user hit logout endpoint
    And user hit get question bank endpoint with id of previous created data
    Then question bank error response code should be 401

  @Negative @QuestionBank
  Scenario: Get question bank with random id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get question bank endpoint with id "id"
    Then question bank error response code should be 404
    And question bank error response status should be "NOT_FOUND"
    And user hit logout endpoint

  @Positive @QuestionBank
  Scenario: Get question bank after logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get question bank endpoint with id of previous created data
    Then question bank response code should be 200
    And question bank response data has title "QA Title 1"
    And question bank response data has description "QA Description 1"

  @Negative @QuestionBank
  Scenario: Update question bank without logging in
    When user hit logout endpoint
    And user hit update question bank endpoint with id of previous created data, title "", and description ""
    Then question bank error response code should be 401

  @Negative @QuestionBank
  Scenario: Update question bank with empty title and description after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit update question bank endpoint with id of previous created data, title "", and description ""
    Then question bank error response code should be 400
    And question bank error response has key "title" and value "NotBlank"
    And question bank error response has key "description" and value "NotBlank"
    And user hit logout endpoint

  @Positive @QuestionBank
  Scenario: Update question bank after logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit update question bank endpoint with id of previous created data, title "QA Title 2", and description "QA Description 2"
    Then question bank response code should be 200
    And question bank response data has title "QA Title 2"
    And question bank response data has description "QA Description 2"

  @Negative @QuestionBank
  Scenario: Delete question bank without logging in
    When user hit logout endpoint
    And user hit delete question bank endpoint with id of previous created data
    Then question bank error response code should be 401

  @Positive @QuestionBank
  Scenario: Delete question bank with logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit delete question bank endpoint with id of previous created data
    Then question bank base response code should be 200
    And user hit get question bank endpoint with id of previous created data
    Then question bank error response code should be 404
    And question bank error response status should be "NOT_FOUND"

  @Negative @QuestionBank
  Scenario: Get all question banks without logging in
    When user hit logout endpoint
    And user hit get all question banks endpoint
    Then question bank error response code should be 401

  @Positive @QuestionBank
  Scenario: Get all question banks with logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get all question banks endpoint
    Then question bank paging response code should be 200
    And question bank paging response data size should be more than 0
    And question bank paging response data should contains title "QA Title 1"
    And question bank paging response data should contains description "QA Description 1"

