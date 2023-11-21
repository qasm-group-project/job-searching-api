#language: en
#utf-8

Feature: Update a job post to filled functionality
  Job provider should be able to update the status of a job post to filled
  to avoid other job seekers to keep applying to them

  Background:
    Given the tables are empty

  Scenario: User is created successfully - response validation
    Given the job seeker is created with
    """
      {
        "username": "username",
        "password": "password",
        "company_name": "company_name",
        "company_contact_number": "company_contact_number",
        "company_location": "company_location"
      }
    """
    And the job seeker is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "jobType": "FULL_TIME",
        "isVisible": "true"
      }
    """
    When I call the update job post path with the following body
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "jobType": "FULL_TIME",
        "isVisible": "true",
        "status": "FILLED"
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "title" returned must be "job post title"
    And the field "description" returned must be "description"
    And the field "salary" returned must be "1000.0"
    And the field "jobType" returned must be "FULL_TIME"
    And the field "status" returned must be "FILLED"
    And the job post with the title "job post title" must have the status "FILLED"