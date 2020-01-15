@File @Regression
Feature: File

  Background:
    Given user prepare file request
    And user prepare auth request
    And user prepare user request
    And user hit logout endpoint

  @Negative @File
  Scenario: Create file as guest
    When user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 401

  @Positive @File
  Scenario: Create file as logged-in user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 201

  @Negative @File
  Scenario: Create folder as guest
    When user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 401

  @Negative @File
  Scenario: Create folder with blank folder name and non-existent type
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FLDR" ""
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 400
    And file/folder error response has key "name" and value "NotBlank"
    And file/folder error response has key "type" and value "TypeMustExist"

  @Negative @File
  Scenario: Create folder with multipart file data
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 400
    And file/folder error response has key "type" and value "TypeAndBytesMustBeValid"

  @Negative @File
  Scenario: Create folder after logging in as student
    And user prepare batch request
    And user prepare resource request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 403
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @File
  Scenario: Create folder as logged-in, non-student user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    Then file/folder response code should be 201

  @Negative @File
  Scenario: Get files/folders as guest
    When user hit files/folders endpoint with parent id "root"
    Then file/folder response code should be 401

  @Positive @File
  Scenario: Get files/folders as logged-in user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit files/folders endpoint with parent id "root"
    Then file/folder response code should be 200
    And file/folder response should not be empty
    And file/folder response path ids should contain "root"

  @Negative @File
  Scenario: Get file as guest
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user hit get file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 401

  @Positive @File
  Scenario: Get file as logged-in user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "UX Function Core.txt"
    And retrieved file version should have key 1

  @Negative @File
  Scenario: Get folder as guest
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user hit get file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 401

  @Positive @File
  Scenario: Get folder as logged-in user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "Sample Folder"

  @Negative @File
  Scenario: Update file as guest
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user create "FILE" "src/test/resources/samples/Screenshot (96).png"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 401

  @Negative @File
  Scenario Outline: Update file as non-owner and non-admin
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create "FILE" "src/test/resources/samples/Screenshot (96).png"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 403
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @File
  Scenario: Update file as file owner
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FILE" "src/test/resources/samples/Screenshot (96).png"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "Screenshot (96).png"
    And retrieved file version should have key 1
    And retrieved file version should have key 2

  @Positive @File
  Scenario: Update non-owned file as admin
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.adm@mailinator.com", name "Admin", role "ADMIN", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user create "FILE" "src/test/resources/samples/UX Function Core.txt"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user prepare auth request
    And user do login with email "qa.adm@mailinator.com" and password "adminfunctionapp"
    And user create "FILE" "src/test/resources/samples/Screenshot (96).png"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "Screenshot (96).png"
    And retrieved file version should have key 1
    And retrieved file version should have key 2
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Admin" and email "qa.adm@mailinator.com"

  @Negative @File
  Scenario: Update folder as guest
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user create "FOLDER" "Sample Folder Updated"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 401

  @Negative @File
  Scenario Outline: Update folder as non-owner and non-admin
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create "FOLDER" "Sample Folder Updated"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 403
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @File
  Scenario: Update folder as folder owner
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create "FOLDER" "Sample Folder Updated"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "Sample Folder Updated"

  @Positive @File
  Scenario: Update non-owned folder as admin
    When user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "qa.adm@mailinator.com", name "Admin", role "ADMIN", address "Address", phone "0815123123123", avatar "", batch code "", university ""
    And user create "FOLDER" "Sample Folder"
    And user hit create file/folder endpoint with parent id "root"
    And user hit logout endpoint
    And user prepare file request
    And user prepare auth request
    And user do login with email "qa.adm@mailinator.com" and password "adminfunctionapp"
    And user create "FOLDER" "Sample Folder Updated"
    And user hit update file/folder endpoint with recorded id and parent id "root"
    Then file/folder response code should be 200
    And retrieved file/folder name should be "Sample Folder Updated"
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Admin" and email "qa.adm@mailinator.com"
