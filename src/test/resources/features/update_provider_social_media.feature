#language: en
#utf-8

Feature: Updating Social Media for a Provider

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
    And the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """

  Scenario: the job provider can update the provider social media successfully!
    And the job provider is logged in with username "username" and password "password"
    When I call the update provider social media path with the following body
    """
      {
        "link": "https://updated-link.com"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Social Media Platform updated successfully"

  Scenario: Job Provider can not update social media because of lacking data!
    And the job provider is logged in with username "username" and password "password"
    When I call the update provider social media path with the following body
    """
      {
        "link": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Provider can not update social media because of lacking data!
    And the job provider is logged in with username "username" and password "password"
    When I call the update provider social media path with the following body
    """
      {

      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Provider can not update social media because of invalid id!
    And the job provider is logged in with username "username" and password "password"
    When I call the update provider social media path with the following body with fake socialMediaId
    """
      {
        "link": "https://updated-link.com"
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Social media platform not found for the given ID and provider"