@Announcement @Regression
Feature: Announcement

  Background:
    Given user prepare announcement request
    And user prepare auth request
    And user prepare resource request
    And user prepare user request
    And user prepare batch request
    And user hit logout endpoint

  @Negative @Announcement
  Scenario: Create announcement without being logged in
    When user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    Then announcement response code should be 401

  @Negative @Announcement
  Scenario Outline: Create announcement after logging in as non-admin and non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    Then announcement response code should be 403
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Announcement
  Scenario: Create announcement after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    Then announcement response code should be 403
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @Announcement
  Scenario: Create announcement with incorrect request formats
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "" and summary "09876543210987654321098765432109876543210987654321098765432109876543210" and description ""
    And user add resource id "" to request
    And user hit create announcement endpoint
    Then announcement response code should be 400
    And announcement error response has key "title" and value "NotBlank"
    And announcement error response has key "summary" and value "Size"
    And announcement error response has key "files" and value "FileMustExist"

  @Positive @Announcement
  Scenario: Create announcement after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "announcement"
    And user hit post resource endpoint
    And user add uploaded resource's id to announcement request
    And user hit create announcement endpoint
    Then announcement response code should be 201
    And created announcement title should be "Title" and summary "Summary" and description "Description"
    And created announcement files should not be empty

  @Positive @Announcement
  Scenario: Get announcements without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user hit announcement endpoint with page 1 and size 4
    Then announcement response code should be 200
    And announcement response data should not be empty

  @Positive @Announcement
  Scenario Outline: Get announcements after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user hit announcement endpoint with page 1 and size 4
    Then announcement response code should be 200
    And announcement response data should not be empty
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @Announcement
  Scenario: Get announcements after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit announcement endpoint with page 1 and size 4
    Then announcement response code should be 200
    And announcement response data should not be empty
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @Announcement
  Scenario: Get announcement detail without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user hit announcement endpoint with recorded id
    Then announcement response code should be 200
    And retrieved announcement title should be "Title" and summary "Summary" and description "Description"

  @Positive @Announcement
  Scenario Outline: Get announcement detail after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user hit announcement endpoint with recorded id
    Then announcement response code should be 200
    And retrieved announcement title should be "Title" and summary "Summary" and description "Description"
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @Announcement
  Scenario: Get announcement detail after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit announcement endpoint with recorded id
    Then announcement response code should be 200
    And retrieved announcement title should be "Title" and summary "Summary" and description "Description"
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @Announcement
  Scenario: Update announcement without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user prepare announcement request
    And user create announcement request with title "Title Updated" and summary "Summary Updated" and description "Description Updated"
    And user hit update announcement endpoint with recorded id
    Then announcement response code should be 401

  @Negative @Announcement
  Scenario Outline: Update announcement after logging in as non-admin and non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user prepare announcement request
    And user do login with email "<email>" and password "<password>"
    And user create announcement request with title "Title Updated" and summary "Summary Updated" and description "Description Updated"
    And user hit update announcement endpoint with recorded id
    Then announcement response code should be 403
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @Announcement
  Scenario: Update announcement after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user hit logout endpoint
    And user prepare announcement request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create announcement request with title "Title Updated" and summary "Summary Updated" and description "Description Updated"
    And user hit update announcement endpoint with recorded id
    Then announcement response code should be 403
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @Announcement
  Scenario: Update announcement with incorrect request formats
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user hit create announcement endpoint
    And user prepare announcement request
    And user create announcement request with title "" and summary "09876543210987654321098765432109876543210987654321098765432109876543210" and description ""
    And user add resource id "" to request
    And user hit update announcement endpoint with recorded id
    Then announcement response code should be 400
    And announcement error response has key "title" and value "NotBlank"
    And announcement error response has key "summary" and value "Size"
    And announcement error response has key "files" and value "FileMustExist"

  @Positive @Announcement
  Scenario: Update announcement after logging in as admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create announcement request with title "Title" and summary "Summary" and description "Description"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "announcement"
    And user hit post resource endpoint
    And user add uploaded resource's id to announcement request
    And user hit create announcement endpoint
    And user prepare announcement request
    And user create announcement request with title "Title Updated" and summary "Summary Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "announcement"
    And user hit post resource endpoint
    And user add uploaded resource's id to announcement request
    And user hit update announcement endpoint with recorded id
    Then announcement response code should be 200
    And retrieved announcement title should be "Title Updated" and summary "Summary Updated" and description "Description Updated"
    And retrieved announcement files should not be empty
