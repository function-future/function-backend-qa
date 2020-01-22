@SharedCourse @Regression
Feature: Shared Course

  Background:
    Given user prepare shared course request with target batch code "TargetBatchCode"
    And user prepare auth request
    And user prepare user request
    And user prepare batch request
    And user prepare resource request
    And user prepare course request
    And user hit logout endpoint

  @Negative @SharedCourse
  Scenario: Create shared course from master course data without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit logout endpoint
    And user prepare auth request
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Create shared course from master course data as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "TargetBatchCode", university "University"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 403

  @Negative @SharedCourse
  Scenario: Create shared course from master course data with non-existent course and duplicate course id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user add "recorded-course-id" to shared course request
    And user add "non-existent-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 400
    And shared course error response has key "courses" and value "CourseMustBeDistinct"
    And shared course error response has key "courses" and value "CourseMustExist"

  @Negative @SharedCourse
  Scenario: Create shared course from master course data with empty course id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create shared course request from batch ""
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 400
    And shared course error response has key "courses" and value "NotEmpty"

  @Positive @SharedCourse
  Scenario Outline: Create shared course from master course data as non-guest and non-student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 201
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @SharedCourse
  Scenario: Create shared course from shared course data without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user create shared course request from batch "TargetBatchCode"
    And user add "first-recorded-shared-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Create shared course from shared course data as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "TargetBatchCode", university "University"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create shared course request from batch "TargetBatchCode"
    And user add "first-recorded-shared-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 403

  @Negative @SharedCourse
  Scenario: Create shared course from shared course data with non-existent course and duplicate course id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create shared course request from batch "TargetBatchCode"
    And user add "first-recorded-shared-course-id" to shared course request
    And user add "first-recorded-shared-course-id" to shared course request
    And user add "non-existent-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 400
    And shared course error response has key "courses" and value "CourseMustBeDistinct"
    And shared course error response has key "courses" and value "CourseMustExist"

  @Negative @SharedCourse
  Scenario: Create shared course from shared course data with empty course id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create shared course request from batch "TargetBatchCode"
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 400
    And shared course error response has key "courses" and value "NotEmpty"

  @Positive @SharedCourse
  Scenario Outline: Create shared course from shared course data as non-guest and non-student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create shared course request from batch ""
    And user add "recorded-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create shared course request from batch "TargetBatchCode"
    And user add "first-recorded-shared-course-id" to shared course request
    And user hit create shared course endpoint for batch "recorded-batch-code"
    Then shared course response code should be 201
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |
