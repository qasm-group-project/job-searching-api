#language: en
#utf-8

Feature: get all Social Media for a Provider

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

  Scenario: get all job Provider social media successfully!
    When the job provider is logged in with username "username" and password "password"
    And a social media is created with
    """
      {
        "platform": "instagram",
        "link": "https://link.com"
      }
    """
    And a social media is created with
    """
      {
        "platform": "linkedin",
        "link": "https://link.com"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"
    And the field "message" returned must be "Provider Social Media Created successfully!"
    When I call get all provider social medias
    Then the status returned must be 200
    Then the field "number_of_social_media_platforms" returned must be "2"