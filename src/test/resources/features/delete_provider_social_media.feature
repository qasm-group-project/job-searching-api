#language: en
#utf-8


Feature: Deleting Social Media for a Provider
  Background:
    Given the tables are empty
    And the job provider is created with
    """
      {
        "username": "username",
        "password": "password",
        "company_name": "company_name",
        "company_contact_number": "3322441423",
        "company_location": "company_location"
      }
    """
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
  Scenario: Job Provider delete social media successfully!
    When the job provider is logged in with username "username" and password "password"
    And I call the delete provider social media path with the following body
    """
      {
      }
    """
    Then the status returned must be 200
  Scenario: Job Provider can not delete social media because of invalid id!
    When the job provider is logged in with username "username" and password "password"
    And I call the delete provider social media path with the following body and fake socialMediaId
    """
      {
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Social media platform not found for the given ID and provider"