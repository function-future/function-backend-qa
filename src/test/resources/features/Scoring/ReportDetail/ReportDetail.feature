@ReportDetail @Scoring @Regression
Feature: Report Detail

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare report request with batchCode "future3"
    And user hit get all report endpoint
    And user store report id from all report response
    And user hit get report with any id from all report response
    And user prepare report detail request

  @Negative @ReportDetail
  Scenario: User give score to students without logging in
    Given user hit logout endpoint
    When user hit give score to students endpoint with first stored student id, minus "false" and score 70
    Then report detail error response code should be 401

  @Negative @ReportDetail
  Scenario: User give score to students with logging in as admin
    When user hit give score to students endpoint with first stored student id, minus "false" and score 70
    Then report detail error response code should be 403
    And user hit logout endpoint

  @Negative @ReportDetail
  Scenario: User give score to students with bad data
    Given user hit logout endpoint
    And user do login with email "oliver@judge.com" and password "oliverfunctionapp"
    When user hit give score to students endpoint with first stored student id, minus "true" and score 70
    Then report detail error response code should be 400
    And report detail error response body should have these data
      | score     | Min   |
    And user hit logout endpoint

  @Positive @ReportDetail
  Scenario: User give score to students with logging in as judge
    Given user hit logout endpoint
    And user do login with email "oliver@judge.com" and password "oliverfunctionapp"
    When user hit give score to students endpoint with first stored student id, minus "false" and score 70
    Then report detail response code should be 201
    And report detail response body score should be 70
    And user hit logout endpoint
