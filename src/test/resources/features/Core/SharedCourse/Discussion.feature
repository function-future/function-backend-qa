@Discussion @SharedCourse @Regression
Feature: Discussion

  Background:
    Given user prepare shared course request with target batch code "TargetBatchCode"
    And user prepare auth request
    And user prepare user request
    And user prepare batch request
    And user prepare resource request
    And user prepare course request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user hit logout endpoint
    And user prepare auth request
    And user prepare discussion request with target batch code "TargetBatchCode" and course "first-recorded-shared-course-id"

  @Negative @Discussion @SharedCourse
  Scenario: Create discussion on a batch's shared course without being logged in
    When user hit create discussion with comment "Comment"
    Then discussion response code should be 401

  @Negative @Discussion @SharedCourse
  Scenario: Create discussion on another batch's shared course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare batch request
    And user hit create batch endpoint with name "QA Batch Name 2" and code "TargetBatchCode2"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "TargetBatchCode2", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit create discussion with comment "Comment"
    Then discussion response code should be 403

  @Positive @Discussion @SharedCourse
  Scenario: Create discussion on his/her batch's shared course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "TargetBatchCode", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit create discussion with comment "Comment"
    Then discussion response code should be 201

  @Positive @Discussion @SharedCourse
  Scenario Outline: Create discussion on any batch's shared course after logging in as non-guest and non-student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user hit create discussion with comment "Comment"
    Then discussion response code should be 201
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Discussion @SharedCourse
  Scenario: Get discussions on a batch's shared course without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create discussion with comment "Comment"
    And user hit logout endpoint
    And user prepare discussion request with target batch code "TargetBatchCode" and course "first-recorded-shared-course-id"
    And user hit discussion endpoint with page 1 and size 4
    Then discussion response code should be 401

  @Negative @Discussion @SharedCourse
  Scenario: Get discussions on another batch's shared course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create discussion with comment "Comment"
    And user prepare batch request
    And user hit create batch endpoint with name "QA Batch Name 2" and code "TargetBatchCode2"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "TargetBatchCode2", university "University"
    And user hit logout endpoint
    And user prepare discussion request with target batch code "TargetBatchCode" and course "first-recorded-shared-course-id"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit discussion endpoint with page 1 and size 4
    Then discussion response code should be 403

  @Positive @Discussion @SharedCourse
  Scenario: Get discussions on his/her batch's shared course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create discussion with comment "Comment"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "TargetBatchCode", university "University"
    And user hit logout endpoint
    And user prepare discussion request with target batch code "TargetBatchCode" and course "first-recorded-shared-course-id"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit discussion endpoint with page 1 and size 4
    Then discussion response code should be 200
    And retrieved discussion response data should not be empty

  @Positive @Discussion @SharedCourse
  Scenario Outline: Get discussions on any batch's shared course after logging in as non-guest and non-student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create discussion with comment "Comment"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare discussion request with target batch code "TargetBatchCode" and course "first-recorded-shared-course-id"
    And user do login with email "<email>" and password "<password>"
    And user hit discussion endpoint with page 1 and size 4
    Then discussion response code should be 200
    And retrieved discussion response data should not be empty
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |
