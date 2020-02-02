@ActivityBlog @Regression
Feature: Activity Blog

  Background:
    Given user prepare activity blog request
    And user prepare auth request
    And user prepare resource request
    And user prepare batch request
    And user prepare user request
    And user hit logout endpoint

  @Negative @ActivityBlog
  Scenario: Create activity blog without being logged in
    When user create activity blog request with title "Title" and description "Description"
    And user hit create activity blog endpoint
    Then activity blog response code should be 401

  @Negative @ActivityBlog
  Scenario: Create activity blog with incorrect request formats
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "" and description ""
    And user add resource id "" to activity blog request
    And user hit create activity blog endpoint
    Then activity blog response code should be 400
    And activity blog error response has key "title" and value "NotBlank"
    And activity blog error response has key "description" and value "NotBlank"
    And activity blog error response has key "files" and value "FileMustExist"

  @Positive @ActivityBlog
  Scenario: Create activity blog after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    Then activity blog response code should be 201
    And created activity blog title should be "Title" and description "Description"
    And created activity blog files should not be empty

  @Positive @ActivityBlog
  Scenario Outline: Create activity blog after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user prepare auth request
    And user do login with email "<email>" and password "<password>"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    Then activity blog response code should be 201
    And created activity blog title should be "Title" and description "Description"
    And created activity blog files should not be empty
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @ActivityBlog
  Scenario: Get activity blogs without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user hit activity blog endpoint with page 1 and size 5
    Then activity blog response code should be 200
    And retrieved activity blog response data should not be empty

  @Positive @ActivityBlog
  Scenario: Get activity blogs after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit activity blog endpoint with page 1 and size 5
    Then activity blog response code should be 200
    And retrieved activity blog response data should not be empty
    And user prepare batch request
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"

  @Positive @ActivityBlog
  Scenario Outline: Get activity blogs after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user hit activity blog endpoint with page 1 and size 5
    Then activity blog response code should be 200
    And retrieved activity blog response data should not be empty
    And user hit logout endpoint
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @ActivityBlog
  Scenario: Get activity blog by specific author by author's id without logging in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user hit activity blog endpoint with page 1 and size 5 and user id "random-id"
    Then activity blog response code should be 200
    And retrieved activity blog response data should be empty

  @Positive @ActivityBlog
  Scenario: Get activity blog by specific author by author's id after logging in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit activity blog endpoint with page 1 and size 5 and user id "current user"
    Then activity blog response code should be 200
    And retrieved activity blog response data should not be empty

  @Positive @ActivityBlog
  Scenario: Get activity blog detail without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user hit activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title" and description "Description"
    And retrieved activity blog files should not be empty

  @Positive @ActivityBlog
  Scenario: Get activity blog detail after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title" and description "Description"
    And retrieved activity blog files should not be empty

  @Positive @ActivityBlog
  Scenario Outline: Get activity blog detail after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user hit activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title" and description "Description"
    And retrieved activity blog files should not be empty
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @ActivityBlog
  Scenario: Update activity blog without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 401

  @Negative @ActivityBlog
  Scenario: Update non-owned activity blog after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 403

  @Negative @ActivityBlog
  Scenario Outline: Update non-owned activity blog after logging in as non-admin and non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 403
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @ActivityBlog
  Scenario Outline: Update non-owned activity blog after logging in as any admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title Updated" and description "Description Updated"
    And retrieved activity blog files should be empty
    Examples:
      | email                 | password         | name  | role  | address | phone         |
      | qa.adm@mailinator.com | adminfunctionapp | Admin | ADMIN | Address | 0815123123123 |

  @Positive @ActivityBlog
  Scenario: Update owned activity blog after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title Updated" and description "Description Updated"
    And retrieved activity blog files should not be empty

  @Positive @ActivityBlog
  Scenario Outline: Update owned activity blog after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user create activity blog request with title "Title Updated" and description "Description Updated"
    And user hit update activity blog endpoint with recorded id
    Then activity blog response code should be 200
    And retrieved activity blog title should be "Title Updated" and description "Description Updated"
    And retrieved activity blog files should be empty
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Negative @ActivityBlog
  Scenario: Delete activity blog without being logged in
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 401

  @Negative @ActivityBlog
  Scenario: Delete non-owned activity blog after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 403

  @Negative @ActivityBlog
  Scenario Outline: Delete non-owned activity blog after logging in as non-admin and non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 403
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |

  @Positive @ActivityBlog
  Scenario Outline: Delete non-owned activity blog after logging in as any admin
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit logout endpoint
    And user prepare activity blog request
    And user do login with email "<email>" and password "<password>"
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 200
    Examples:
      | email                 | password         | name  | role  | address | phone         |
      | qa.adm@mailinator.com | adminfunctionapp | Admin | ADMIN | Address | 0815123123123 |

  @Positive @ActivityBlog
  Scenario: Delete owned activity blog after logging in as student role
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create batch endpoint with name "QA Batch Name" and code "BatchCodeAutomation"
    And user hit create user endpoint with email "qa.student@mailinator.com", name "Student", role "STUDENT", address "Address", phone "0815123123123", avatar "no-avatar", batch code "BatchCodeAutomation", university "University"
    And user hit logout endpoint
    And user do login with email "qa.student@mailinator.com" and password "studentfunctionapp"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 200

  @Positive @ActivityBlog
  Scenario Outline: Delete owned activity blog after logging in as non-student roles
    When user do login with email "admin@admin.com" and password "administratorfunctionapp"
    And user hit create user endpoint with email "<email>", name "<name>", role "<role>", address "<address>", phone "<phone>", avatar "no-avatar", batch code "", university ""
    And user hit logout endpoint
    And user do login with email "<email>" and password "<password>"
    And user create activity blog request with title "Title" and description "Description"
    And user select file "src/test/resources/samples/Screenshot (96).png" to be uploaded to origin "blog"
    And user hit post resource endpoint
    And user add uploaded resource's id to activity blog request
    And user hit create activity blog endpoint
    And user hit delete activity blog endpoint with recorded id
    Then activity blog response code should be 200
    Examples:
      | email                    | password          | name   | role   | address | phone         |
      | qa.adm@mailinator.com    | adminfunctionapp  | Admin  | ADMIN  | Address | 0815123123123 |
      | qa.judge@mailinator.com  | judgefunctionapp  | Judge  | JUDGE  | Address | 0815123123123 |
      | qa.mentor@mailinator.com | mentorfunctionapp | Mentor | MENTOR | Address | 0815123123123 |
