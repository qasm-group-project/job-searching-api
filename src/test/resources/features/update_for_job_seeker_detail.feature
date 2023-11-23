#language: en
#utf-8

Feature: As a job seeker i want to update my personal detail

  Background:
    Given the tables are empty

  Scenario: The detail is updated successfully
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "password":"update test",
        "nickname":"update_test",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 200
    And the field "message" returned must be "Update success!"
    And the field "token" returned must be "not null"
    And the field "Seeker.username" returned must be "dfsd1f111566666"
    And the field "Seeker.nickname" returned must be "update_test"
    And the field "Seeker.email" returned must be "update_test"
    And the field "Seeker.phone" returned must be "update_test"
    And the field "Seeker.firstname" returned must be "update_test"
    And the field "Seeker.lastname" returned must be "update_test"
    And the field "Seeker.gender" returned must be "male"

  Scenario: The detail is updated failed - gender required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 200
    And the field "message" returned must be "Update success!"
    And the field "token" returned must be "not null"
    And the field "Seeker.username" returned must be "dfsd1f111566666"
    And the field "Seeker.nickname" returned must be "update_test"
    And the field "Seeker.email" returned must be "update_test"
    And the field "Seeker.phone" returned must be "update_test"
    And the field "Seeker.firstname" returned must be "update_test"
    And the field "Seeker.lastname" returned must be "update_test"
    And the field "Seeker.gender" returned must be "male"

  Scenario: The detail is updated failed - gender required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The gender is required."



  Scenario: The detail is updated failed - nickname required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The nickname is required."

  Scenario: The detail is updated failed - email required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The email is required."

  Scenario: The detail is updated failed - phone required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"update_test",
        "phone":"",
        "firstname":"update_test",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The phone is required."

  Scenario: The detail is updated failed - firstname required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"",
        "lastname":"update_test",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The firstname is required."

  Scenario: The detail is updated failed - lastname required
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the update job post path with the following body job
    """
      {
        "nickname":"update_test",
        "email":"update_test",
        "phone":"update_test",
        "firstname":"update_test",
        "lastname":"",
        "gender":"male"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The lastname is required."