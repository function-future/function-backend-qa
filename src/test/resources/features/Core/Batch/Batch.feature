@Batch @Regression
Feature: Batch

  Background:
    Given user prepare batch request
    And user prepare auth request
    And user hit logout endpoint

  @Negative @Batch
  Scenario: Create batch without logging in
    When user hit create batch endpoint with name "Batch Name" and code "Batch3"
    Then batch response code should be 401

  @Negative @Batch
  Scenario: Create batch with empty name and empty code after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "" and code ""
    Then batch response code should be 400
    And batch error response has key "name" and value "NotBlank"
    And batch error response has key "code" and value "NotBlank"
    And batch error response has key "code" and value "Alphanumeric"

  @Negative @Batch
  Scenario: Create batch with name and code has space and non alphanumeric after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name" and code "Batch 3~"
    Then batch response code should be 400
    And batch error response has key "code" and value "NoSpace"
    And batch error response has key "code" and value "Alphanumeric"

  @Positive @Batch
  Scenario: Create batch after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name" and code "Batch3"
    Then batch response code should be 201

  @Negative @Batch
  Scenario: Create batch with existing code after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name" and code "Batch3"
    And user hit create batch endpoint with name "Batch Name" and code "Batch3"
    Then batch response code should be 400
    And batch error response has key "code" and value "UniqueBatchCode"

  @Negative @Batch
  Scenario: Get batches after logging out
    When user hit batch endpoint with page 1 and size 5
    Then batch response code should be 401

  @Positive @Batch
  Scenario: Get batches after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name" and code "Batch3"
    And user hit batch endpoint with page 1 and size 5
    Then batch response code should be 200
    And batch response should contain name "Batch Name"
    And batch response should contain code "Batch3"

  @Negative @Batch
  Scenario: Get batch detail after logging out
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name 4" and code "Batch4"
    And user hit logout endpoint
    And user prepare batch request
    And user hit batch detail endpoint with recorded id
    Then batch response code should be 401

  @Positive @Batch
  Scenario: Get batch detail after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name 5" and code "Batch5"
    And user hit batch detail endpoint with recorded id
    Then batch response code should be 200
    And batch response's name should be "Batch Name 5"
    And batch response's code should be "Batch5"

  @Negative @Batch
  Scenario: Edit batch without being logged in
    When user hit edit batch endpoint with recorded id and name "Batch Name 5 Updated" and code "Batch5Updated"
    Then batch response code should be 401

  @Negative @Batch
  Scenario: Edit batch with empty name and empty code after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "" and code ""
    Then batch response code should be 400
    And batch error response has key "name" and value "NotBlank"
    And batch error response has key "code" and value "NotBlank"
    And batch error response has key "code" and value "Alphanumeric"

  @Negative @Batch
  Scenario: Edit batch with name and code has space and non alphanumeric after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name 6" and code "Batch 6~"
    Then batch response code should be 400
    And batch error response has key "code" and value "NoSpace"
    And batch error response has key "code" and value "Alphanumeric"

  @Positive @Batch
  Scenario: Edit batch after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name 6" and code "Batch6"
    And user hit edit batch endpoint with recorded id and name "Batch Name 6 Updated" and code "Batch6Updated"
    Then batch response code should be 200
    And batch response's name should be "Batch Name 6 Updated"
    And batch response's code should be "Batch6Updated"

  @Negative @Batch
  Scenario: Delete batch without being logged in
    When user hit delete batch endpoint with recorded id
    Then batch response code should be 401

  @Positive @Batch
  Scenario: Delete batch after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name 7" and code "Batch7"
    And user hit delete batch endpoint with recorded id
    Then batch response code should be 200
