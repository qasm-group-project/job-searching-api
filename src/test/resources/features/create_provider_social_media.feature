#language: en
#utf-8


# This Test has written by Mohammad Oveisi - Student-Id: 239008124



Feature: Adding Social Media for a Provider

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

  Scenario: Job Provider create social media successfully!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Provider Social Media Created successfully!"

  Scenario: Job Provider can not create social media because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Provider can not create social media because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full link is required."

  Scenario: Job Provider can not create social media because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "link": "https://link.com"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full platform is required."

  Scenario: Job Provider can not create social media because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full platform is required."

  Scenario: Providers are unable to generate a duplicated platform.
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Provider Social Media Created successfully!"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Platform already exists for this provider"