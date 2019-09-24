@User @Regression
Feature: User

  Background:
    Given user prepare user request
    And user prepare auth request
    And user prepare batch request
    And user prepare resource request
    And user hit logout endpoint

  @Negative @User
  Scenario: Create user without being logged in
    When user hit create user endpoint with email "qa.adm@mailinator.com", name "Admin Admin", role "ADMIN", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    Then user response code should be 401

  @Negative @User
  Scenario Outline: Create user with non-student role with incorrect request formats
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name for User Automation" and code "BatchUserAutomation"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "<avatar>", batch code "<batch_code>", university "<university>"
    Then user response code should be 400
    And user error response has key "address" and value "NotBlank"
    And user error response has key "email" and value "Email"
    And user error response has key "name" and value "Name"
    And user error response has key "phone" and value "Phone"
    And user error response has key "role" and value "OnlyStudentCanHaveBatchAndUniversity"
    And user error response has key "avatar" and value "FileMustExist"
    And user hit delete batch endpoint with recorded id
    Examples:
      | email               | name            | role   | address | phone            | avatar    | batch_code          | university |
      | qa.admmailinator    | Admin Admin 1   | ADMIN  |         | 0815123123123asd | sample-id | BatchUserAutomation | University |
      | qa.judgemailinator  | Judge Judge 1   | JUDGE  |         | 0815123123123asd | sample-id | BatchUserAutomation | University |
      | qa.mentormailinator | Mentor Mentor 1 | MENTOR |         | 0815123123123asd | sample-id | BatchUserAutomation | University |

  @Positive @User
  Scenario Outline: Create user with non-student role after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "<avatar>", batch code "<batch_code>", university "<university>"
    Then user response code should be 201
    Examples:
      | email                    | name          | role   | address | phone         | avatar | batch_code | university |
      | qa.adm@mailinator.com    | Admin Admin   | ADMIN  | Address | 0815123123123 |        |            |            |
      | qa.judge@mailinator.com  | Judge Judge   | JUDGE  | Address | 0815123123123 |        |            |            |
      | qa.mentor@mailinator.com | Mentor Mentor | MENTOR | Address | 0815123123123 |        |            |            |

  @Negative @User
  Scenario: Create user with role mentor after logging in as mentor
    When user do login with email "qa.mentor@mailinator.com" and password "mentormentorfunctionapp"
    And user hit create user endpoint with email "qa.mentor.2@mailinator.com", name "Mentor Mentor Two", role "MENTOR", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    Then user response code should be 403

  @Negative @User
  Scenario: Create user with role student after logging in as admin with empty batch and university
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name for User Automation" and code "BatchUserAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student First", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    Then user response code should be 400
    And user error response has key "role" and value "OnlyStudentCanHaveBatchAndUniversity"
    And user hit delete batch endpoint with recorded id

  @Negative @User
  Scenario: Create user with role student after logging in as admin with non-existent batch
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student First", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchUserAutomation", university "University"
    Then user response code should be 400
    And user error response has key "batch" and value "BatchMustExist"

  @Positive @User
  Scenario: Create user with role student after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name for User Automation" and code "BatchUserAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student First", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchUserAutomation", university "University"
    Then user response code should be 201
    And user hit delete batch endpoint with recorded id

  @Negative @User
  Scenario: Get user without being logged in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.judge.2@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user prepare user request
    And user hit get user detail endpoint with recorded id
    Then user response code should be 401

  @Positive @User
  Scenario: Get user after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.mentor.2@mailinator.com", name "Mentor", role "MENTOR", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit get user detail endpoint with recorded id
    Then user response code should be 200
    And user response's email should be "qa.mentor.2@mailinator.com"

  @Negative @User
  Scenario: Get users without being logged in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.judge.3@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.4@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user prepare user request
    And user hit get users endpoint with role "JUDGE", page 1, size 10
    Then user response code should be 401

  @Positive @User
  Scenario: Get users after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.judge.5@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.6@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit get users endpoint with role "JUDGE", page 1, size 10
    Then user response code should be 200
    And user response's total elements should be 6
    #And user response's total elements should be 2 || Uncomment this after implementing delete user mechanism

  @Negative @User
  Scenario: Get users by name without being logged in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.judge.7@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.8@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user prepare user request
    And user hit get users by name endpoint with name part "dge", page 1, size 10
    Then user response code should be 401

  @Positive @User
  Scenario Outline: Get users by name after logging in as non-judge role
    When user do login with email "<email>" and password "<password>"
    And user hit create user endpoint with email "qa.judge.9@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.10@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user hit get users by name endpoint with name part "dge", page 1, size 10
    Then user response code should be 200
    And user response's total elements should be 10
    #And user response's total elements should be 2 || Uncomment this after implementing delete user mechanism
    Examples:
      | email                     | password                 |
      | admin@admin.com           | administratorfunctionapp |
      | qa.mentor@mailinator.com  | mentormentorfunctionapp  |
      | qa.student@mailinator.com | studentfirstfunctionapp  |
