#language: en
#utf-8


# This Test has written by Yekta Roustaei - Student-Id: 239042144



Feature: Updating Social Media for a Seeker

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
    And the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """

  Scenario: the job seeker can update the seeker social media successfully!
    And the job seeker is logged in with username "username" and password "password"
    When I call the update seeker social media path with the following body
      """
        {
          "link": "https://updated-link.com"
        }
      """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Social Media Platform updated successfully"

  Scenario: Job Seeker can not update social media because of lacking data!
    And the job seeker is logged in with username "username" and password "password"
    When I call the update seeker social media path with the following body
    """
      {
        "link": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Seeker can not update social media because of lacking data!
    And the job seeker is logged in with username "username" and password "password"
    When I call the update seeker social media path with the following body
    """
      {

      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Seeker can not update social media because of invalid id!
    And the job seeker is logged in with username "username" and password "password"
    When I call the update seeker social media path with the following body with fake socialMediaId
    """
      {
        "link": "https://updated-link.com"
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Social media platform not found for the given ID and seeker"