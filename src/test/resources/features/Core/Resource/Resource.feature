@Resource @Regression
Feature: Resource

  Background:
    Given user prepare resource request
    And user prepare auth request

  @Negative @Resource
  Scenario: Post resource without being logged in
    When user hit logout endpoint
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "user"
    And user hit post resource endpoint
    Then resource response code should be 401

  @Positive @Resource
  Scenario: Post resource text after logging in as admin
    When user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "user"
    And user hit post resource endpoint
    Then resource response code should be 201
    And resource should have file "full" url
    And resource should not have file "thumbnail" url

  @Positive @Resource
  Scenario: Post resource image after logging in as admin
    When user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    Then resource response code should be 201
    And resource should have file "full" url
    And resource should have file "thumbnail" url

  @Positive @Resource
  Scenario: Get resource text after logging in as admin
    When user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/UX Function Core.txt" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user get last uploaded file path "full"
    Then resource response body as bytes should not be empty

  @Positive @Resource
  Scenario: Get resource image full url after logging in as admin
    When user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user get last uploaded file path "full"
    Then resource response body as bytes should not be empty

  @Positive @Resource
  Scenario: Get resource image thumbnail url after logging in as admin
    When user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "user"
    And user hit post resource endpoint
    And user get last uploaded file path "thumbnail"
    Then resource response body as bytes should not be empty
