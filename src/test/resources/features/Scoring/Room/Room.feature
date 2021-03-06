@Room @Scoring @Regression
Feature: Room Feature

    Background:
        Given user prepare auth request
        When user do login with email "admin@admin.com" and password "administratorfunctionapp"
        And user prepare assignment request with batchCode "future3"
        And user hit get all assignment endpoint
        And user get first assignment id and store id
        And user prepare user request
        And user hit get users endpoint with role "STUDENT", page 1, size 10
        And user get first student id and store id
        Then user prepare room request with batchCode "future3"

    @Negative @Room
    Scenario: Get or Create Room without logging in
        Given user hit logout endpoint
        When user hit get or create room endpoint
        Then room error response code should be 401

    @Positive @Room
    Scenario: Get or Create Room Success
        When user hit get or create room endpoint
        Then room response code should be 200
        And room response body assignment id should be the previous fetched assignment id
        And room response body student id should be the previous fetched student id;
        And user hit logout endpoint

    @Negative @Room
    Scenario: Update Room Score without logging in
        Given user hit logout endpoint
        When user hit update room score endpoint with score 90
        Then room error response code should be 401

    @Negative @Room
    Scenario: Update Room Score as Admin
        When user hit update room score endpoint with score 90
        Then room error response code should be 403
        And user hit logout endpoint

    @Negative @Room
    Scenario: Update Room Score as Mentor with minus point
        Given user hit logout endpoint
        When user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
        And user hit update room score endpoint with score - 10
        Then room error response code should be 400
        And room error response body should have key "point" and value "Min"
        And user hit logout endpoint

    @Negative @Room
    Scenario: Update Room Score as Mentor with minus point
        Given user hit logout endpoint
        When user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
        And user hit update room score endpoint with score null
        Then room error response code should be 400
        And room error response body should have key "point" and value "NotNull"
        And user hit logout endpoint

    @Positive @Room
    Scenario: Update room score as Mentor
        Given user hit logout endpoint
        When user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
        And user hit update room score endpoint with score 90
        Then room response code should be 200
        And room response body point should be 90
        And user hit logout endpoint

    @Negative @Room
    Scenario: Delete room without logging in
        Given user hit logout endpoint
        When user hit delete room endpoint
        Then room error response code should be 401

    @Negative @Room
    Scenario: Delete room with logging in as Mentor
        Given user hit logout endpoint
        When user do login with email "oliver@mentor.com" and password "oliverfunctionapp"
        And user hit delete room endpoint
        Then room error response code should be 403
        And user hit logout endpoint

    @Positive @Room
    Scenario: Delete room with logging in as admin
        When user hit delete room endpoint
        Then room base response code should be 200
        And user hit logout endpoint
