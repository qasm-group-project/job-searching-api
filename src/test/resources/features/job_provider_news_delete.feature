#language: en
#utf-8


# This Test has written by Yekta Roustaei - Student-Id: 239042144



Feature: Deleting news for a Provider

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

  Scenario: Job Provider delete news successfully!
    When I call the delete provider news path with the following body
    """
      {
      }
    """
    Then the status returned must be 200
    And the field "message" returned must be "Provider News deleted successfully"

  Scenario: Job Provider can not delete news because of invalid news id!
    When I call the delete provider news path with fake id and the following body
    """
      {
      }
    """
    Then the status returned must be 403
    And the field "message" returned must be "Provider news not found for the given ID and provider"
