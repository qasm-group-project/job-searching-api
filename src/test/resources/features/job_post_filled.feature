#language: en
#utf-8


# This Test has written by Luis Brienze - Student-Id: 239060399



Feature: Update a job post to filled functionality
  Job provider should be able to update the status of a job post to filled
  to avoid other job seekers to keep applying to them

  Background:
    Given the tables are empty

  Scenario: Job post is update successfully to FILLED
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
        "is_visible": "true"
      }
    """
    When I call the update job post path with the following body
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "status": "FILLED"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "title" returned must be "job post title"
    And the field "description" returned must be "description"
    And the field "salary" returned must be "1000.0"
    And the field "job_type" returned must be "FULL_TIME"
    And the field "status" returned must be "FILLED"
    And the job post with the title "job post title" must have the status "FILLED"

  Scenario: Job post is created with status PENDING
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
    And the job post with the title "job post title" must have the status "PENDING"