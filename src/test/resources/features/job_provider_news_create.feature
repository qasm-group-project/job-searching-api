#language: en
#utf-8

Feature: Adding news for a Provider

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

  Scenario: Job Provider create news successfully!
    When the job provider is logged in with username "username" and password "password"
    And news is created with
    """
      {
    "title": "new news 2",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Provider News Created successfully!"

  Scenario: Job Provider can not create news because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And news is created with
    """
      {
        "title": "new news 2"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full description is required."

  Scenario: Job Provider can not create news because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And news is created with
    """
      {
        "title": "new news 2",
        "description": ""
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full description is required."

  Scenario: Job Provider can not create news because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And news is created with
    """
      {
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full title is required."

  Scenario: Job Provider can not create news because of lacking data!
    When the job provider is logged in with username "username" and password "password"
    And news is created with
    """
      {
        "title": "",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
      }
    """
    Then the status returned must be 400
    And the field "errors.0" returned must be "The full title is required."