@ActivityBlog @Regression
Feature: Course

  Background:
    Given user prepare course request
    And user prepare auth request
    And user prepare resource request
    And user prepare batch request
    And user prepare user request
    And user hit logout endpoint

  @Negative @Course
  Scenario: Create course without being logged in
    When user create course request with title "Title" and description "Description"
    And user hit create course endpoint
    Then course response code should be 401

  @Negative @Course
  Scenario: Create course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    When user create course request with title "Title" and description "Description"
    And user hit create course endpoint
    Then course response code should be 403
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @Course
  Scenario: Create course with incorrect request formats
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "" and description ""
    And user add resource id "" to course request
    And user add resource id "" to course request
    And user hit create course endpoint
    Then course response code should be 400
    And course error response has key "title" and value "NotBlank"
    And course error response has key "description" and value "NotBlank"
    And course error response has key "material" and value "FileMustExist"
    And course error response has key "material" and value "Size"

  @Positive @Course
  Scenario Outline: Create course after logging in as either admin, judge, or mentor role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    Then course response code should be 201
    And created course title should be "Title" and description "Description"
    And created course material and material id should not be null
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Course
  Scenario: Get courses without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user hit course endpoint with page 1 and size 5
    Then course response code should be 401

  @Negative @Course
  Scenario: Get courses after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit course endpoint with page 1 and size 5
    Then course response code should be 403
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @Course
  Scenario Outline: Get courses after logging in as either admin, judge, or mentor role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user prepare auth request
    And user prepare course request
    And user do login with email "<email>" and password "<password>"
    And user hit course endpoint with page 1 and size 5
    Then course response code should be 200
    And course response data should not be empty
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Course
  Scenario: Get course detail without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user hit course endpoint with recorded id
    Then course response code should be 401

  @Negative @Course
  Scenario: Get course detail after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit course endpoint with recorded id
    Then course response code should be 403
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @Course
  Scenario Outline: Get course detail after logging in as either admin, judge, or mentor role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user prepare auth request
    And user prepare course request
    And user do login with email "<email>" and password "<password>"
    And user hit course endpoint with recorded id
    Then course response code should be 200
    And retrieved course title should be "Title" and description "Description"
    And retrieved course material and material id should not be null
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Course
  Scenario: Update course without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user create course request with title "Title Updated" and description "Description Updated"
    And user hit update course endpoint with recorded id
    Then course response code should be 401

  @Negative @Course
  Scenario: Update course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare resource request
    And user prepare course request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user replace course request material with uploaded resource's id
    And user hit update course endpoint with recorded id
    Then course response code should be 403
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @Course
  Scenario Outline: Update course after logging in as either admin, judge, or mentor role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user prepare auth request
    And user prepare resource request
    And user prepare course request
    And user do login with email "<email>" and password "<password>"
    And user create course request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user replace course request material with uploaded resource's id
    And user hit update course endpoint with recorded id
    Then course response code should be 200
    And retrieved course title should be "Title Updated" and description "Description Updated"
    And retrieved course material and material id should not be null
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Course
  Scenario: Delete course without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare course request
    And user hit delete course endpoint with recorded id
    Then course response code should be 401

  @Negative @Course
  Scenario: Delete course after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user hit logout endpoint
    And user prepare resource request
    And user prepare course request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit delete course endpoint with recorded id
    Then course response code should be 403
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @Course
  Scenario Outline: Delete course after logging in as either admin, judge, or mentor role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create course request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "course"
    And user hit post resource endpoint
    And user add uploaded resource's id to course request
    And user hit create course endpoint
    And user prepare auth request
    And user prepare resource request
    And user prepare course request
    And user do login with email "<email>" and password "<password>"
    And user hit delete course endpoint with recorded id
    Then course response code should be 200
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |
