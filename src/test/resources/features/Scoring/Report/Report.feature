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

  @Negative @Report
  Scenario: User hit get report without logging in
    When user hit get all report endpoint
    And user hit logout endpoint
    And user hit get report with any id from all report response
    Then report error response code should be 401

  @Positive @Report
  Scenario: User hit get report with logging in as admin
    When user hit get all report endpoint
    And user store report id from all report response
    And user hit get report with any id from all report response
    Then report response code should be 200
    And report response body should have not equal with these data
      | name            | ""    |
      | description     | ""    |
      | studentCount    | 1-4   |

  @Negative @Report
  Scenario: User hit get all report without logging in
    Given user hit logout endpoint
    When user hit get all report endpoint
    Then report error response code should be 401

  @Positive @Report
  Scenario: User hit get all report with logging in as admin
    When user hit get all report endpoint
    Then report paging response code should be 200
    And report paging response body should have not equals with these data
      | name            | ""    |
      | description     | ""    |
      | studentCount    | 1-4   |

  @Negative @Report
  Scenario: User hit update report without logging in
    When user hit get all report endpoint
    And user store report id from all report response
    And user hit logout endpoint
    And user hit update report endpoint with these data
      | name          | description               |
      | Final Judging | Final Judging Description |
    Then report error response code should be 401

  @Negative @Report
  Scenario: User hit update report with empty parameters
    When user hit get all report endpoint
    And user store report id from all report response
    And user hit update report endpoint with these data
      | name          |   |
      | Final Judging |   |
    Then report error response code should be 400
    And report error response body should contains these data
      | name        | Final Judging             |
      | description | Final Judging Description |

  @Positive @Report
  Scenario: User hit update report with logging in as admin
    When user hit get all report endpoint
    And user store report id from all report response
    And user hit update report endpoint with these data
      | name          | description               |
      | Final Judging | Final Judging Description |
    Then report response code should be 200
    And report response body should have not equal with these data
      | name            | ""    |
      | description     | ""    |
      | studentCount    | 1-4   |




