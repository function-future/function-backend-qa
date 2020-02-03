@Notification @Regression
Feature: Notification

  Background:
    Given user prepare auth request
    And user prepare notification request

  @Negative
  Scenario: Create notification without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit logout endpoint
    And user hit create notification with description "description" and title "title"
    Then notification response code should be 401

  @Negative
  Scenario: Get notification without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit logout endpoint
    And user hit get notifications
    Then notification response code should be 401

  @Negative
  Scenario: Get total unseen notification without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit logout endpoint
    And user hit get total unseen
    Then notification response code should be 401

  @Negative
  Scenario: Read notification without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit get total unseen
    Then total unseen notification should be 1

    When user hit logout endpoint
    And user hit read notification
    Then notification response code should be 401


  @Positive
  Scenario: Create notification after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    Then notification response code should be 200
    And notification response description should be "description"
    And notification response title should be "title"

  @Positive
  Scenario: Get notification after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit get notifications
    Then notification response code should be 200

  @Positive
  Scenario: Get total unseen notification after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit get total unseen
    Then notification response code should be 200
    And total unseen notification should be 1

  @Positive
  Scenario: Read notification after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create notification with description "description" and title "title"
    And user hit get total unseen
    Then total unseen notification should be 1

    When user hit read notification
    Then notification response code should be 200

    When user hit get total unseen
    Then total unseen notification should be 0