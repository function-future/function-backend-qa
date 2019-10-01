Feature: Report Feature

  Background:
    Given user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare report request with batchCode "futur3"

  @Negative @Report
  Scenario: User hit get students within batch without logging in
    Given user hit logout endpoint
    When user hit get students within batch
    Then report error response code should be 401

  @Positive @Report
  Scenario: User hit get students within batch as admin
    When user hit get students within batch
    Then student paging response code should be 200
    And student paging response body should not be null

  @Negative @Report
  Scenario: User hit create report without logging in
    Given user hit logout endpoint
    When user hit create report with name "Final Judging Session 1" and description "Final Judging Session 1 Description" and "students"
    Then report error response code should be 401

  @Negative @Report
  Scenario: User hit create report with empty name, description, and zero list of students
    When user hit create report with name "" and description "" and ""
    Then report error response code should be 400
    And report error response body should contains these data
      | name        | NotBlank |
      | description | NotBlank |
      | students    | Size     |

  @Positive @Report
  Scenario: User hit create report without logging in
    When user hit create report with name "Final Judging Session 1" and description "Final Judging Session 1 Description" and "students"
    Then report response code should be 201
    And report response body should have these data
      | name        | Final Judging Session 1             |
      | description | Final Judging Session 1 Description |
