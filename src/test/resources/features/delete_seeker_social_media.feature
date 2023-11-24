#language: en
#utf-8


Feature: Deleting Social Media for a Seeker
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
    When the job seeker is logged in with username "username" and password "password"
    And a seeker social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
  Scenario: Job Seeker delete social media successfully!
    When the job seeker is logged in with username "username" and password "password"
    And I call the delete seeker social media path with the following body
    """
      {
      }
    """
    Then the status returned must be 200
  Scenario: Job Seeker can not delete social media because of invalid id!
    When the job seeker is logged in with username "username" and password "password"
    And I call the delete seeker social media path with the following body and fake socialMediaId
    """
      {
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Social media platform not found for the given ID and seeker"