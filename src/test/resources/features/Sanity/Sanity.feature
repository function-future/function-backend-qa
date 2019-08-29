@Sanity @Regression
Feature: Sanity

  @Positive @Sanity
  Scenario: Sanity test by checking connection to Google page
    Given user prepare request
    When user hit Google endpoint
    Then response code should be 200
