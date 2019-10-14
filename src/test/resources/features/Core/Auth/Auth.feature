@Auth @Regression
Feature: Auth

  Background:
    Given user prepare auth request
    And user hit logout endpoint
    And user prepare auth request

  @Negative @Auth
  Scenario: Get login status before logging in
    When user hit auth endpoint
    Then auth response should be unauthorized

  @Negative @Auth
  Scenario: Login with incorrect credentials
    When user do login with email "admin@admin.com" and password "simplepassword"
    Then auth response should be unauthorized

  @Positive @Auth
  Scenario: Login with admin credentials - first created user
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    Then auth response should be ok and cookie is present

  @Positive @Auth
  Scenario: Get login status after logging in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit auth endpoint with cookie
    Then auth response should be ok and cookie is present

  @Positive @Auth
  Scenario: Logout after logging in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit logout endpoint
    Then auth response should be ok and cookie is unset

  @Negative @Auth
  Scenario: Get login status after logging out
    When user hit auth endpoint
    Then auth response should be unauthorized
