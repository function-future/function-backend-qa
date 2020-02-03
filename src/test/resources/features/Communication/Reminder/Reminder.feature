@Reminder @Regression
Feature: Reminder

  Background:
    Given user prepare auth request
    And user prepare reminder request

  @Negative
  Scenario: Create reminder before login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit logout endpoint
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    Then reminder response code should be 401

  @Negative
  Scenario: Get reminder list before login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit logout endpoint
    And user hit get reminders
    Then reminder response code should be 401

  @Negative
  Scenario: Get reminder detail before login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit logout endpoint
    And user hit get reminder detail
    Then reminder response code should be 401

  @Negative
  Scenario: Update reminder before login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    Then reminder response code should be 200
    And reminder response description should be "description"
    And reminder response title should be "title"

    When user hit logout endpoint
    And user hit update reminder with title "newtitle", description "newdescription", hour 12, minute 40
    Then reminder response code should be 401

  @Negative
  Scenario: Delete reminder before login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit logout endpoint
    And user hit delete reminder
    Then reminder response code should be 401

  @Positive
  Scenario: Create reminder after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    Then reminder response code should be 200
    And reminder response description should be "description"
    And reminder response title should be "title"

  @Positive
  Scenario: Get reminder list after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit get reminders
    Then reminder response code should be 200
    And reminder response first data description should be "description"
    And reminder response first data title should be "title"

  @Positive
  Scenario: Get reminder detail after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit get reminder detail
    Then reminder response code should be 200
    And reminder response description should be "description"
    And reminder response title should be "title"

  @Positive
  Scenario: Update reminder after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    Then reminder response code should be 200
    And reminder response description should be "description"
    And reminder response title should be "title"

    When user hit update reminder with title "newtitle", description "newdescription", hour 12, minute 40
    Then reminder response code should be 200
    And reminder response description should be "newdescription"
    And reminder response title should be "newtitle"

  @Positive
  Scenario: Delete reminder after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create reminder endpoint with description "description", hour 10, minute 40, title "title", monthlyDate 20
    And user hit delete reminder
    Then reminder response code should be 200
