@Assignment @Scoring @Regression
Feature: Assignment Feature

  Background:
    Given user prepare auth request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare assignment request with batchCode "future3"

  @Negative @Assignment
  Scenario: user create assignment without logging in
    Given user hit logout endpoint
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1679171744868, and empty list of files
    Then assignment error response code should be 401

  @Negative @CreateAssignmentWithNotValidRequest
  Scenario: user create assignment with empty title and description
    When user hit create assignment endpoint with title "", description "", deadline 1679171744868, and empty list of files
    Then assignment error response code should be 400
    And assignment error response body should have key "title" and value "NotBlank"
    And assignment error response body should have key "description" and value "NotBlank"
    And user hit logout endpoint

  @Positive @CreateAssignmentWithNoFile
  Scenario: user create assignment with logging in as admin
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1679171744868, and empty list of files
    Then assignment response code should be 201
    And assignment response body title should be "Assignment Title"
    And assignment response body description should be "Assignment Description"
    And assignment response body deadline should be 1679171744868
    And user hit logout endpoint

  @Negative @CreateAssignmentWithPassedDeadline
  Scenario: user create assignment with passed deadline and logging in as admin
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 15000000, and empty list of files
    Then assignment response code should be 201
    And assignment response body title should be "Assignment Title"
    And assignment response body description should be "Assignment Description"
    And assignment response body deadline should be 1679171744868
    And user hit logout endpoint

  @Positive @CreateAssignmentWithFile
  Scenario: user create assignment with file and logging in as admin
    When user prepare resource request
    And user select file "src/test/resources/samples/Sample.docx" to be uploaded to origin "user"
    And user hit post resource endpoint
    Then resource response code should be 201
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1679171744868, and uploaded file
    Then assignment error response code should be 400
    And assignment error response body should have key "deadline" and value "DateNotPassed"
    And user hit logout endpoint

  @Negative @CreateAssignmentWithWrongExtensionFile
  Scenario: user create assignment with wrong extension file and logging in as admin
    When user prepare resource request
    And user select file "src/test/resources/samples/Screenshot (42).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    Then resource response code should be 201
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1679171744868, and uploaded file
    Then assignment error response code should be 400
    And assignment error response body should have key "files" and value "ExtensionMustBeValid"
    And user hit logout endpoint

  @Negative @CreateAssignmentWithNonExistFile
  Scenario: user create assignment with non-exist file and logging in as admin
    When user hit create assignment endpoint with title "Assignment Title", description "Assignment Description", deadline 1679171744868, and non-exist file
    Then assignment error response code should be 400
    And assignment error response body should have key "files" and value "FileMustExist"
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: user get all assignment without logging in
    Given user hit logout endpoint
    When user hit get all assignment endpoint
    Then assignment error response code should be 401

  @Positive @Assignment
  Scenario: user get all assignment with logging in as admin
    When user hit get all assignment endpoint
    Then assignment paging response code should be 200
    And assignment paging response body should contains title "Assignment Title" and description "Assignment Description"
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: user get assignment by previous created id and without logging in
    Given user hit logout endpoint
    When user hit get assignment endpoint with previous created id
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: user get assignment with random id
    When user get assignment endpoint with random id
    Then assignment error response code should be 404
    And assignment error response status should be "NOT_FOUND"
    And user hit logout endpoint

  @Positive @Assignment
  Scenario: user get assignment by previous created id and logging in as admin
    When user hit get assignment endpoint with previous created id
    Then assignment response code should be 200
    And assignment response body title should be "Assignment Title"
    And assignment response body description should be "Assignment Description"
    And assignment response body deadline should be 1679171744868
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: user update assignment without logging in
    Given user hit logout endpoint
    When user hit update assignment endpoint with previous get id, title "Assignment Title", description "Assignment Description", deadline 1679171744868, and empty list of files
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: user update assignment with empty title and description
    When user hit update assignment endpoint with previous get id, title "", description "", deadline 1679171744868, and empty list of files
    Then assignment error response code should be 400
    And assignment error response body should have key "title" and value "NotBlank"
    And assignment error response body should have key "description" and value "NotBlank"
    And user hit logout endpoint

  @Positive @Assignment
  Scenario: user update assignment with logging in as admin
    When user hit update assignment endpoint with previous get id, title "Assignment Title 2", description "Assignment Description 2", deadline 1679171744868, and empty list of files
    Then assignment response code should be 200
    And assignment response body title should be "Assignment Title 2"
    And assignment response body description should be "Assignment Description 2"
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: Copy assignment without logging in
    Given user hit logout endpoint
    When user hit copy assignment with batchCode "future4" and assignment id of previous get id
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: Copy assignment with random assignment id
    When user hit copy assignment with batchCode"future4" and random assignment id
    Then assignment error response code should be 400
    And assignment error response status should be "BAD_REQUEST"
    And assignment error response body should have key "assignmentId" and value "AssignmentMustExist"
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: Copy assignment with random batch code
    When user hit copy assignment with batchCode "random" and assignment id of previous get id
    Then assignment error response code should be 400
    And assignment error response status should be "BAD_REQUEST"
    And assignment error response body should have key "batchCode" and value "BatchMustExist"
    And user hit logout endpoint

  @Positive @Assignment
  Scenario: Copy assignment with logging in as admin
    When user hit copy assignment with batchCode "future4" and assignment id of previous get id
    Then assignment response code should be 201
    And assignment response body title should be "Assignment Title 2"
    And assignment response body description should be "Assignment Description 2"
    When user prepare assignment request with batchCode "future4"
    And user hit get all assignment endpoint
    Then assignment paging response body should contains title "Assignment Title 2" and description "Assignment Description 2"
    And user hit logout endpoint

  @Negative @Assignment
  Scenario: user delete assignment without logging in
    Given user hit logout endpoint
    When user hit delete assignment endpoint with previous get id
    Then assignment error response code should be 401

  @Negative @Assignment
  Scenario: user delete assignment with random id
    When user hit delete assignment endpoint with random id
    Then assignment error response code should be 200
    And assignment error response status should be "OK"
    And user hit logout endpoint

  @Positive @Assignment
  Scenario: user delete assignment with previous get id and logging in as admin
    When user hit delete assignment endpoint with previous get id
    Then assignment base response code should be 200
    When user hit get assignment endpoint with previous created id
    Then assignment error response code should be 404
    And assignment error response status should be "NOT_FOUND"
    And user hit logout endpoint
