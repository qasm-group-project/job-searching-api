#language: en
#utf-8


# This Test has written by Yekta Roustaei - Student-Id: 239042144



Feature: Update or Create a job post with setting deadline for it
  Job provider should be able to set deadline for job posts

  Background:
    Given the tables are empty
    And the job provider is created with
    """
      {
        "username": "username",
        "password": "password",
        "company_name": "company_name",
        "company_contact_number": "company_contact_number",
        "company_location": "company_location"
      }
    """

  Scenario: create job post with setting specific deadline time for it
    And the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"

  Scenario: create job post without setting specific deadline time for it
    And the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"

  Scenario: create job post without setting specific deadline time for it
    And the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    Then the status returned must be 201
    And the field "id" returned must be "not null"

  Scenario: update job post with setting specific deadline time for it
    And the job provider is logged in with username "username" and password "password"
    When a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    Then the status returned must be 201
    And I call the update job post path with the following body
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"

  Scenario: update job post with setting specific deadline time for it
    And the job provider is logged in with username "username" and password "password"
    When a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-12-25T23:00:00"
      }
    """
    Then the status returned must be 201
    And I call the update job post path with the following body
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"