@QuestionBank @Regression
Feature: Question Bank

  Background:
    Given user prepare question bank request

  @Negative @QuestionBank
  Scenario: Create question bank without logging in
    And user prepare auth request
    When user hit logout endpoint
    And user hit create question bank endpoint with title "QA Title" and description "QA Description"
    Then question bank response code should be 401

  @Negative @QuestionBank
  Scenario: Create question bank with empty title and description after logging in as admin
    And user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create question bank endpoint with title "" and description ""
    Then question bank response code should be 400
    And question bank error response has key "title" and value "NotBlank"
    And question bank error response has key "description" and value "NotBlank"
