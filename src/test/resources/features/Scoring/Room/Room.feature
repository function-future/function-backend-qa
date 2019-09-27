@Room
Feature: Room

    Background:
        Given user prepare auth request
        When user do login with email "admin@admin.com" and password "administratorfunctionapp"
        And user prepare assignment request with batchCode "futur3"
        And user hit get all assignment endpoint
        And user get first assignment id and store id
        And user prepare user request
        And user hit get users endpoint with role "STUDENT", page 1, size 10
        And user get first student id and store id
        Then user prepare room request

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
        And room response body point should be 0
