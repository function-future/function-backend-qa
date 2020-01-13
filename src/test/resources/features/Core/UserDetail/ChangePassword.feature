@UserDetail @ChangePassword @Regression
Feature: Change Password

  Background:
    Given user prepare change password request
    And user prepare auth request
    And user prepare user request
    And user prepare batch request
    And user hit logout endpoint

  @Negative @UserDetail @ChangePassword
  Scenario: Change password without being logged in
    When user hit change password endpoint with old password "oldpassword" and new password "newpassword"
    Then change password response code should be 401

  @Negative @UserDetail @ChangePassword
  Scenario Outline: Change password after logging in as non-student roles with incorrect old password
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<oldpassword>"
    And user hit change password endpoint with old password "incorrectoldpassword" and new password "newpassword"
    Then change password response code should be 401
    And user hit logout endpoint
    And user do login with email "<email>" and password "<oldpassword>"
    And auth response should be ok and cookie is present
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | name   | role   | address | phone         | oldpassword       |
      | qa.adm@mailinator.com    | Admin  | ADMIN  | Address | 0815123123123 | adminfunctionapp  |
      | qa.judge@mailinator.com  | Judge  | JUDGE  | Address | 0815123123123 | judgefunctionapp  |
      | qa.mentor@mailinator.com | Mentor | MENTOR | Address | 0815123123123 | mentorfunctionapp |

  @Negative @UserDetail @ChangePassword
  Scenario: Change password after logging in as student role with incorrect old password
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit change password endpoint with old password "incorrectoldpassword" and new password "newpassword"
    Then change password response code should be 401
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And auth response should be ok and cookie is present
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @UserDetail @ChangePassword
  Scenario Outline: Change password after logging in as non-student roles with incorrect old password
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<oldpassword>"
    And user hit change password endpoint with old password "<oldpassword>" and new password "newpassword"
    Then change password response code should be 200
    And user hit logout endpoint
    And user do login with email "<email>" and password "<oldpassword>"
    And auth response should be unauthorized
    And user do login with email "<email>" and password "newpassword"
    And auth response should be ok and cookie is present
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | name   | role   | address | phone         | oldpassword       |
      | qa.adm@mailinator.com    | Admin  | ADMIN  | Address | 0815123123123 | adminfunctionapp  |
      | qa.judge@mailinator.com  | Judge  | JUDGE  | Address | 0815123123123 | judgefunctionapp  |
      | qa.mentor@mailinator.com | Mentor | MENTOR | Address | 0815123123123 | mentorfunctionapp |

  @Positive @UserDetail @ChangePassword
  Scenario: Change password after logging in as student role with incorrect old password
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit change password endpoint with old password "studentfunctionapp" and new password "newpassword"
    Then change password response code should be 200
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And auth response should be unauthorized
    And user do login with email "qa.student@mailinator.com" and password "newpassword"
    And auth response should be ok and cookie is present
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id
