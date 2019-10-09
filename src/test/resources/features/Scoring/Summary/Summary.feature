Feature: Student Summary

  Background:
    Given user prepare auth request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user prepare user request
    And user hit get users endpoint with role "STUDENT", page 1, size 10
    And user store first student id from all students
    And user prepare summary request

  @Negative @Summary
  Scenario: User hit summary endpoint without logging in
    Given user hit logout endpoint
    When user hit get summary endpoint with type "Quiz"
    Then summary error response code should be 401

  @Negative @Summary
  Scenario: User as student want to access another student's summary
    Given user hit logout endpoint
    And user do login with email "david@gmail.com" and password "oliverfunctionapp"
    When user hit get summary endpoint with type "Quiz"
    Then summary error response code should be 403

  @Positive @Summary
  Scenario: User as admin want to access summary of a student
    When user hit get summary endpoint with type "Quiz"
    Then summary response code should be 200
    And summary response body studentId should be the same with stored student id
    And summary response body scores should all match type "QUIZ"

  @Positive @Summary
  Scenario: User as student want to access his/her own summary
    Given user hit logout endpoint
    And user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    When user hit get summary endpoint with type "Quiz"
    Then summary response code should be 200
    And summary response body studentId should be the same with stored student id
    And summary response body scores should all match type "QUIZ"

  @Positive @Summary
  Scenario: User as student want to access his/her own summary
    Given user hit logout endpoint
    And user do login with email "oliver@gmail.com" and password "oliverfunctionapp"
    When user hit get summary endpoint with type "Assignment"
    Then summary response code should be 200
    And summary response body studentId should be the same with stored student id
    And summary response body scores should all match type "ASSIGNMENT"

  @Positive @Summary
  Scenario: User as Mentor want to access a student's summary
    Given user hit logout endpoint
    And user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
    When user hit get summary endpoint with type "Quiz"
    Then summary response code should be 200
    And summary response body studentId should be the same with stored student id
    And summary response body scores should all match type "QUIZ"

  @Positive @Summary
  Scenario: User as Judge want to access a student's summary
    Given user hit logout endpoint
    And user do login with email "oliver@judge.com" and password "oliverfunctionapp"
    When user hit get summary endpoint with type "Quiz"
    Then summary response code should be 200
    And summary response body studentId should be the same with stored student id
    And summary response body scores should all match type "QUIZ"

