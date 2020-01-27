@UserDetail @Profile @Regression
Feature: Profile

  Background:
    Given user prepare profile request
    And user prepare auth request
    And user prepare batch request
    And user prepare user request
    And user prepare resource request
    And user hit logout endpoint

  @Negative @UserDetail @Profile
  Scenario: Get profile without being logged in
    When user hit get profile endpoint
    Then profile response code should be 401

  @Positive @UserDetail @Profile
  Scenario Outline: Get profile after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user hit get profile endpoint
    Then profile response code should be 200
    And profile response email should be "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>"
    And profile response picture should not be empty
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | name   | role   | address | phone         | password          |
      | qa.adm@mailinator.com    | Admin  | ADMIN  | Address | 0815123123123 | adminfunctionapp  |
      | qa.judge@mailinator.com  | Judge  | JUDGE  | Address | 0815123123123 | judgefunctionapp  |
      | qa.mentor@mailinator.com | Mentor | MENTOR | Address | 0815123123123 | mentorfunctionapp |

  @Positive @UserDetail @Profile
  Scenario: Get profile after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit get profile endpoint
    Then profile response code should be 200
    And profile response email should be "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123"
    And profile response picture should be empty
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id
