#language: en
#utf-8

Feature: As a provider
  I want to retrieve expired job posts based on their deadline
  So that I can manage expired job posts effectively

  Background:
    Given the tables are empty

  Scenario: Retrieve expired job posts
    Given the job provider is created with
    """
      {
        "username": "username",
        "password": "password",
        "company_name": "company_name",
        "company_contact_number": "company_contact_number",
        "company_location": "company_location"
      }
    """
    And the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-10-30T23:00:00"
      }
    """
    And a post is created with
    """
      {
        "title": "job post title 2",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-10-31T23:00:00"
      }
    """
    When I request to get expired job posts with this body
    """
      {
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "job post title"
    And the field "content.1.title" returned must be "job post title 2"
    And the field "totalElements" returned must be "2"