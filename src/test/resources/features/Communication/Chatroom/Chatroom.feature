@Chatroom @Regression
Feature: Chatroom

  Background:
    Given user prepare chatroom request
    And user prepare auth request
    And user prepare user request

  @Negative
  Scenario: Create chatroom without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit logout endpoint
    And user hit create chatroom with name "chatroom name"
    Then chatroom response code should be 401

  @Negative
  Scenario: Get chatroom lists without login
    When user hit get chatroom list endpoint
    Then chatroom response code should be 401

  @Negative
  Scenario: Get chatroom detail without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit logout endpoint
    And user hit get chatroom detail
    Then chatroom response code should be 401

  @Negative
  Scenario: Set chatroom limit without login
    When user hit set chatroom limit endpoint with limit equals 10
    Then chatroom response code should be 401

  @Negative
  Scenario: Unset chatroom limit without login
    When user hit unset chatroom limit endpoint
    Then chatroom response code should be 401

  @Negative
  Scenario: Update chatroom without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit logout endpoint
    And user hit update chatroom with name "new chatroom name", picture ""
    Then chatroom response code should be 401

  @Negative
  Scenario: Enter chatroom without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit logout endpoint
    And user hit enter chatroom endpoint
    Then chatroom response code should be 401

  @Negative
  Scenario: Leave chatroom without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit logout endpoint
    And user hit leave chatroom endpoint
    Then chatroom response code should be 401

  @Negative
  Scenario: Create message without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit logout endpoint
    And user hit create message with content "message 1"
    Then chatroom response code should be 401

  @Negative
  Scenario: Get message list after pivot without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit logout endpoint
    And user hit get messages after pivot
    Then chatroom response code should be 401

  @Negative
  Scenario: Get message list before pivot without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit logout endpoint
    And user hit get messages before pivot
    Then chatroom response code should be 401

  @Negative
  Scenario: Get message list without login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit logout endpoint
    And user hit get messages
    Then chatroom response code should be 401

  @Negative
  Scenario: Update message status after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit logout endpoint
    And user hit update message status
    Then chatroom response code should be 401

  @Positive
  Scenario: Create chatroom after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    Then chatroom response code should be 200
    And chatroom has name "chatroom name"
    And chatroom has member with name "Agung"
    And chatroom has member with name "Admin Istrator"
    And chatroom has member with name "Judge"

  @Positive
  Scenario: Get chatroom lists after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit get chatroom list endpoint
    Then chatroom response code should be 200

  @Positive
  Scenario: Set chatroom limit after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit set chatroom limit endpoint with limit equals 10
    Then chatroom response code should be 200

  @Positive
  Scenario: Unset chatroom limit after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit unset chatroom limit endpoint
    Then chatroom response code should be 200

  @Positive
  Scenario: Get chatroom detail after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit get chatroom detail
    Then chatroom response code should be 200
    And chatroom has name "chatroom name"
    And chatroom has member with name "Agung"
    And chatroom has member with name "Admin Istrator"
    And chatroom has member with name "Judge"

  @Positive
  Scenario: Update chatroom after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit update chatroom with name "new chatroom name", picture ""
    Then chatroom response code should be 200
    And chatroom has name "new chatroom name"
    And chatroom has member with name "Agung"
    And chatroom has member with name "Admin Istrator"
    And chatroom has member with name "Judge"

  @Positive
  Scenario: Enter chatroom after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit enter chatroom endpoint
    Then chatroom response code should be 200

  @Positive
  Scenario: Leave chatroom after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit leave chatroom endpoint
    Then chatroom response code should be 200

  @Positive
  Scenario: Create message after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    Then chatroom response code should be 201

  @Positive
  Scenario: Get message list after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit get messages
    Then chatroom response code should be 200
    And last message should be "message 1"

  @Positive
  Scenario: Get message list after pivot after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit get messages after pivot
    Then chatroom response code should be 200
    And last message should be "message 2"

  @Positive
  Scenario: Get message list before pivot after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit get messages before pivot
    Then chatroom response code should be 200
    And last message should be "message 1"

  @Positive
  Scenario: Update message status after login
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "asdfghasdf@gmail.com", name "Agung", role "MENTOR", address "Bandung", phone "08561229561", avatar "no-avatar", batch code "", university ""
    And user hit create user endpoint with email "qa.judge.1@mailinator.com", name "Judge", role "JUDGE", address "Address", phone "0815123123123", avatar "no-avatar", batch code "", university ""
    And user hit get users by name endpoint with name part "", page 1, size 10
    And user hit create chatroom with name "chatroom name"
    And user hit create message with content "message 1"
    And user hit create message with content "message 2"
    And user hit get messages
    And user hit update message status
    Then chatroom response code should be 200
