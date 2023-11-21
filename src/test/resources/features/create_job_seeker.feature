#language: en
#utf-8

Feature: Create a job seeker functionality
  Job seeker should be able to create an account
  to search and apply for jobs

  Background:
    Given the tables are empty

  Scenario: Job seeker is created successfully
    When I call the create job seeker path with the following body
    """
      {
        "username":"dfsd1f111566666",
        "password":"testacpasswor",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
    Then the status returned must be 200
    And the field "message" returned must be "Login success!"
    And the field "token" returned must be "not null"
    And the field "user.username" returned must be "dfsd1f111566666"
    And the field "user.password" returned must be "not null"
    And the field "user.nickname" returned must be "test_nickname"
    And the field "user.email" returned must be "test"
    And the field "user.phone" returned must be "444412345"
    And the field "user.firstname" returned must be "testirstname"
    And the field "user.lastname" returned must be "testlastname"
    And the field "user.gender" returned must be "male"

  Scenario: Job seeker creation failed - username required
    When I call the create job seeker path with the following body
    """
      {
        "password":"testacpasswor",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The username is required."

  Scenario: Job seeker creation failed - password required
    When I call the create job seeker path with the following body
    """
      {
        "username":"dfsd1f111566666",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The password is required."

  Scenario: Job seeker creation failed - nickname required
    When I call the create job seeker path with the following body
    """
      {
        "username":"dfsd1f111566666",
         "password":"testacpasswor",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The nickname is required."

  Scenario: Job seeker creation failed - email required
    When I call the create job seeker path with the following body
    """
      {
        "username":"dfsd1f111566666",
        "password":"testacpasswor",
        "nickname":"test_nickname",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The email is required."
