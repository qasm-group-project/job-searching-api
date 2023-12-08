#language: en
#utf-8



# This Test has written by Nuntipat Pantarag - Student-Id: 239010006


  
Feature: Job seeker make visibility to employers
  Background:
    Given the tables are empty
    And the job seeker is created with
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

  Scenario: Job seeker visible profile
    When I call the visible seeker with the following body
    """
      {
      }
    """
    Then the status returned must be 200

  Scenario: Job seeker invisible profile
    When I call the invisible seeker with the following body
    """
      {
      }
    """
    Then the status returned must be 200
