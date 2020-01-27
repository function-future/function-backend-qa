@UserDetail @ChangeProfilePicture @Regression
Feature: Change Profile Picture

  Background:
    Given user prepare change profile picture request
    And user prepare auth request
    And user prepare user request
    And user prepare batch request
    And user prepare resource request
    And user hit logout endpoint

  @Negative @UserDetail @ChangeProfilePicture
  Scenario: Change profile picture without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user prepare resource request
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit logout endpoint
    And user prepare change profile picture request with avatar id "default"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 401
    And user prepare batch request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @UserDetail @ChangeProfilePicture
  Scenario: Change profile picture with multiple avatar id
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare resource request
    And user prepare change profile picture request with avatar id "default"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare change profile picture request with avatar id "default"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 400
    And change profile picture error response should have key "avatar" and value "Size"
    And user prepare batch request
    And qa system do cleanup data for recorded avatars
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @UserDetail @ChangeProfilePicture
  Scenario: Change profile picture without existing file
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user prepare resource request
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare change profile picture request with avatar id "no-avatar-id"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 400
    And change profile picture error response should have key "avatar" and value "FileMustExist"
    And user prepare batch request
    And qa system do cleanup data for recorded avatars
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Negative @UserDetail @ChangeProfilePicture
  Scenario: Change profile picture with non-image file
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user prepare resource request
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare change profile picture request with avatar id "default"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 400
    And change profile picture error response should have key "avatar" and value "FileMustBeImage"
    And user prepare batch request
    And qa system do cleanup data for recorded avatars
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @UserDetail @ChangeProfilePicture
  Scenario: Change profile picture after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user prepare resource request
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare change profile picture request with avatar id "default"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 200
    And user prepare batch request
    And qa system do cleanup data for recorded avatars
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "Student" and email "qa.student@mailinator.com"
    And user hit delete batch endpoint with recorded id

  @Positive @UserDetail @ChangeProfilePicture
  Scenario Outline: Change profile picture after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user prepare resource request
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user prepare change profile picture request with avatar id "default"
    And user hit change profile picture endpoint
    Then change profile picture endpoint response code should be 200
    And qa system do cleanup data for recorded avatars
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And qa system do cleanup data for user with name "<name>" and email "<email>"
    Examples:
      | email                    | name   | role   | address | phone         | password          |
      | qa.adm@mailinator.com    | Admin  | ADMIN  | Address | 0815123123123 | adminfunctionapp  |
      | qa.judge@mailinator.com  | Judge  | JUDGE  | Address | 0815123123123 | judgefunctionapp  |
      | qa.mentor@mailinator.com | Mentor | MENTOR | Address | 0815123123123 | mentorfunctionapp |
