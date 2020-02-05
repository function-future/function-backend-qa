@Logging @Regression
Feature: Logging
  Background:
    Given user prepare logging room request
    And user prepare auth request
    And user prepare batch request
    And user prepare user request
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "Batch Name" and code "Batch3"
    And user hit create user endpoint with email "agung@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "student@mail.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "Batch3", university "ITB"
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit logout endpoint

  @Negative
  Scenario: Create logging room without login
    When user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 401

  @Negative
  Scenario: get logging room without login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And user hit logout endpoint
    And user hit get logging rooms
    Then logging room response code should be 400

  @Negative
  Scenario: create topic without login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit logout endpoint
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 401

  @Negative
  Scenario: get topic detail without login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 201
    And topic paging response size equal 1
    And user hit logout endpoint
    And user hit get topic detail
    Then topic title is "topic title"

  @Positive
  Scenario: Create logging room with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201

  @Positive
  Scenario: get logging rooms with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1

  @Positive
  Scenario: get logging room detail with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And user hit get logging room detail
    Then logging room title is "title" and description "description"

  @Positive
  Scenario: create topic with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 201
    And topic paging response size equal 1

  @Positive
  Scenario: get topic detail with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 201
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"

  @Positive @BugLogging
  Scenario: create log message with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 201
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"
    And user hit logout endpoint
    When user do login with email "student@mail.com" and password "studentfunctionapp"
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"
    And user hit logout endpoint
    When user do login with email "student@mail.com" and password "studentfunctionapp"
    And  user hit get logging rooms
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"
    And user hit create log message with text "log message"
    Then logging room response code should be 201

  @Positive
  Scenario: get log message with login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit create topic on logging room with title "topic title"
    Then logging room response code should be 201
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"
    And user hit logout endpoint
    When user do login with email "student@mail.com" and password "studentfunctionapp"
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And user hit get topics
    Then topic paging response size equal 1
    And user hit get topic detail
    Then topic title is "topic title"
    And user hit create log message with text "log message"
    Then logging room response code should be 201
    And user hit get log messages
    Then log messages paging response size equal 1
    And log message text is "log message"