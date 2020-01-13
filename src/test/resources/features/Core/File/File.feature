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
