#language: en
#utf-8

Feature: Updating news for a Provider

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
    And news is created with
    """
      {
    "title": "new news 2",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """

  Scenario: Job Provider update news successfully!
    When I call the update provider news path with the following body
    """
      {
    "title": "new news",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Provider News updated successfully"

  Scenario: Job Provider can not update news because of lacking data!
    When I call the update provider news path with the following body
    """
      {
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full title is required."

  Scenario: Job Provider can not update news because of lacking data!
    When I call the update provider news path with the following body
    """
      {
      "title": "",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full title is required."

  Scenario: Job Provider can not update news because of lacking data!
    When I call the update provider news path with the following body
    """
      {
      "title": "new news",
    "description": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full description is required."

  Scenario: Job Provider can not update news because of lacking data!
    When I call the update provider news path with the following body
    """
      {
      "title": "new news"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full description is required."

  Scenario: Job Provider can not update news because of invalid news id!
    When I call the update provider news path with fake id and the following body
    """
      {
    "title": "new news 2",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Provider news not found for the given ID and provider"
