@SharedCourse @Core @Regression
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

  @Negative @SharedCourse
  Scenario: Get shared courses without being logged in
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
    And user hit logout endpoint
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user hit shared course endpoint with page 1 and size 5
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Get shared courses of another batch after logging in as student
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
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit shared course endpoint with page 1 and size 5
    Then shared course response code should be 403

  @Positive @SharedCourse
  Scenario: Get shared courses of his/her batch after logging in as student role
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit shared course endpoint with page 1 and size 5
    Then shared course response code should be 200
    And shared course response data should not be empty

  @Negative @SharedCourse
  Scenario: Get shared courses of non-existent batch after logging in
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "NonExistentBatchCode"
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit shared course endpoint with page 1 and size 5
    Then shared course response code should be 404

  @Positive @SharedCourse
  Scenario Outline: Get shared courses of any batch after logging in as non-guest and non-student role
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
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "<email>" and password "<password>"
    And user hit shared course endpoint with page 1 and size 5
    Then shared course response code should be 200
    And shared course response data should not be empty
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @SharedCourse
  Scenario: Get shared course detail without being logged in
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
    And user hit logout endpoint
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user hit shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Get shared course detail of non-existent batch
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
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare shared course request with target batch code "NonExistentBatchCode"
    And user hit shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 404

  @Negative @SharedCourse
  Scenario: Get shared course detail of another batch after logging in as student role
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
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 403

  @Positive @SharedCourse
  Scenario: Get shared course detail of his/her batch after logging in as student role
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 200

  @Negative @SharedCourse
  Scenario: Get shared course detail of non-existent shared course after logging in as non-guest and non-student role
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit shared course endpoint with "non-existent-shared-course-id"
    Then shared course response code should be 404

  @Positive @SharedCourse
  Scenario Outline: Get shared course detail of any batch after logging in as non-guest and non-student role
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
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "<email>" and password "<password>"
    And user hit shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 200
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @SharedCourse
  Scenario: Update shared course without being logged in
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
    And user hit logout endpoint
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit update shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Update shared course of another batch after logging in as student role
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
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user prepare resource request
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit update shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 403

  @Negative @SharedCourse
  Scenario: Update shared course of his/her batch after logging in as student role
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit update shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 403

  @Positive @SharedCourse
  Scenario Outline: Update shared course of any batch after logging in as non-guest and non-student role
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
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "<email>" and password "<password>"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit update shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 200
    And retrieved shared course response data should have title "Title Updated" and description "Description Updated"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @SharedCourse
  Scenario: Delete shared course without being logged in
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
    And user hit logout endpoint
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user hit delete shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 401

  @Negative @SharedCourse
  Scenario: Delete shared course of another batch after logging in as student role
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
    And user hit create batch endpoint with name "QA Batch Name" and code "TargetBatchCode2"
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode2"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit delete shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 403

  @Negative @SharedCourse
  Scenario: Delete shared course of his/her batch after logging in as student role
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
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit delete shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 403

  @Positive @SharedCourse
  Scenario Outline: Delete shared course of any batch after logging in as non-guest and non-student role
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
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user prepare auth request
    And user prepare shared course request with target batch code "TargetBatchCode"
    And user do login with email "<email>" and password "<password>"
    And user hit delete shared course endpoint with "first-recorded-shared-course-id"
    Then shared course response code should be 200
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |
