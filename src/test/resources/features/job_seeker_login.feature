#language: en
#utf-8


# This Test has written by Luis Brienze - Student-Id: 239060399



Feature: Job seeker login functionality
  Job seeker should be able to login into his account
  to search and apply for jobs

  Background:
    Given the tables are empty

  Scenario: Job seeker logs in successfully
    Given the job seeker is created with
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
    When I call the login path with username "dfsd1f111566666" and password "testacpasswor"
    Then the status returned must be 200
    And the field "message" returned must be "Login success"
    And the field "token" returned must be "not null"

  Scenario: Job seeker does not log in - password incorrect
    Given the job seeker is created with
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
    When I call the login path with username "dfsd1f111566666" and password "testacpasswoa"
    Then the status returned must be 403
    And the field "error" returned must be "Wrong credentials"

  Scenario: Job seeker does not log in - username incorrect
    Given the job seeker is created with
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
    When I call the login path with username "dfsd1f111566663" and password "testacpasswor"
    Then the status returned must be 403
    And the field "error" returned must be "Wrong credentials"

