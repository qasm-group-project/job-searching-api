#language: en
#utf-8


# This Test has written by Yekta Roustaei - Student-Id: 239042144


Feature: Adding Social Media for a seeker

  Background:
    Given the tables are empty
    And the job seeker is created with
    """
      {
        "username":"username",
        "password":"password",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """

  Scenario: Job Seeker create social media successfully!
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Seeker Social Media Created successfully!"

  Scenario: Job Seeker can not create social media because of lacking data!
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Seeker can not create social media because of lacking data!
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Seeker can not create social media because of lacking data!
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "link": "https://link.com"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full platform is required."

  Scenario: Job Seeker can not create social media because of lacking data!
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full platform is required."

  Scenario: Seekers are unable to generate a duplicated platform.
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Seeker Social Media Created successfully!"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Platform already exists for this seeker"