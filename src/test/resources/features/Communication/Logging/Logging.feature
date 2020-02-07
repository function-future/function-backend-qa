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
    And user prepare auth request
    When user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 401

  @Negative
  Scenario: get logging room without login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And user hit logout endpoint
    And user prepare logging room request
    And user hit get logging rooms
    Then logging room response code should be 401

  @Negative
  Scenario: create topic without login
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And  user hit logout endpoint
    And user prepare logging room request
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
    And user prepare auth request
    And user hit get topic detail
    Then topic title is "topic title"

  @Negative
  Scenario: create log message without login
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
    And  user prepare logging room request
    And user hit create log message with text "log message"
    Then logging room response code should be 401

  @Negative
  Scenario: get log message without login
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
    And user prepare logging room request
    And user hit create log message with text "log message"
    Then logging room response code should be 401

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

  @Positive
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
    And  user prepare logging room request
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
    And user prepare logging room request
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

  @Positive
  Scenario: update logging room detail with login
    And user prepare logging room request
    And user prepare user request
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And user hit get logging room detail
    Then logging room title is "title" and description "description"
    And user hit update logging room with title to "updated title" and description to "updated description"
    Then logging room response code should be 200
    Then logging room title is "updated title" and description "updated description"

  @Positive
  Scenario: delete logging room detail with login
    And user prepare logging room request
    And user prepare user request
    When user do login with email "agung@gmail.com" and password "agungfunctionapp"
    And  user hit create logging room with title "title" and description "description" and member "STUDENT"
    Then logging room response code should be 201
    And  user hit get logging rooms
    Then logging room response code should be 200
    And  logging room paging response size equal 1
    And user hit get logging room detail
    Then logging room title is "title" and description "description"
    And user hit delete logging room
    Then logging room response code should be 200

  @Positive @LoggingNew
  Scenario: update topic detail with login
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
    And user hit update topic with title "updated title"
    Then logging room response code should be 200
    Then topic title is "updated title"

  @Positive @LoggingNew
  Scenario: delete topic detail with login
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
    And user hit delete topic
    Then logging room response code should be 200
