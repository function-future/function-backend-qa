@StickyNote @Regression
Feature: Sticky Notes

  Background:
    Given user prepare sticky note request
    And user prepare auth request
    And user hit logout endpoint

  @Negative @StickyNote
  Scenario: Create sticky note without logging in
    When user hit create sticky note endpoint with title "QA Title" and description "QA Description"
    Then sticky note response code should be 401

  @Negative @StickyNote
  Scenario: Create sticky note with empty title and description after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create sticky note endpoint with title "" and description ""
    Then sticky note response code should be 400
    And sticky note error response has key "title" and value "NotBlank"
    And sticky note error response has key "description" and value "NotBlank"

  @Positive @StickyNote
  Scenario: Create sticky note after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create sticky note endpoint with title "QA Title" and description "QA Description"
    Then sticky note response code should be 201

  @Positive @StickyNote
  Scenario: Get sticky note after logging in as admin and creating sticky note
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create sticky note endpoint with title "QA Title" and description "QA Description"
    And user hit sticky note endpoint
    Then sticky note response code should be 200
    And sticky note title should be "QA Title" and description "QA Description"

  @Positive @StickyNote
  Scenario: Get sticky note without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create sticky note endpoint with title "QA Title" and description "QA Description"
    And user hit logout endpoint
    And user prepare sticky note request
    And user hit sticky note endpoint
    Then sticky note response code should be 200
    And sticky note title should be "QA Title" and description "QA Description"
