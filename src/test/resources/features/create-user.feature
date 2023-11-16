#language: en
#utf-8

@all @create_user
Feature: Create user functionality
  User should be able to create its user
  to authenticate in the system later on

  Background:
    Given the tables are empty

  @success @response_validation
  Scenario: User is created successfully - response validation
    When I call the user creation path with the following body
    """
      {
        "name": "Luis",
        "type": "Admin"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "name" returned must be "Luis"
    And the field "type" returned must be "Admin"