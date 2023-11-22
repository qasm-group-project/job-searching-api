#language: en
#utf-8

Feature: Find available jobs functionality
  Job seeker should be able to search for available jobs
  to search and apply for jobs

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
    And the job seeker is created with
    """
      {
        "username":"dfsd1f111566666",
        "password":"testacpasswor",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """

  Scenario: Jobs are returned successfully
    Given the job provider is logged in with username "username" and password "password"
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the field "0.id" returned must be "not null"
    And the field "0.title" returned must be "job post title"
    And the field "0.description" returned must be "description"
    And the field "0.salary" returned must be "1000.0"
    And the field "0.job_type" returned must be "FULL_TIME"
    And the field "0.status" returned must be "PENDING"

  Scenario: Seeker is unauthorized
    Given the job provider is logged in with username "username" and password "password"
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
    And the header is empty
    When I call the search jobs path
    Then the status returned must be 401
    And the field "error" returned must be "User not authorized"

  Scenario: Invisible jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
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
    And a post is created with
    """
      {
        "title": "invisible job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "false"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "1"
    And the field "0.id" returned must be "not null"
    And the field "0.title" returned must be "job post title"
    And the field "0.description" returned must be "description"
    And the field "0.salary" returned must be "1000.0"
    And the field "0.job_type" returned must be "FULL_TIME"
    And the field "0.status" returned must be "PENDING"

  Scenario: Invisible jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "invisible job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "false"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "0"

  Scenario: Canceled jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
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
    And a post is created with
    """
      {
        "title": "Canceled job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    And I call the update job post path with the following body
    """
      {
        "title": "Canceled job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "status": "CANCELED"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "1"
    And the field "0.id" returned must be "not null"
    And the field "0.title" returned must be "job post title"
    And the field "0.description" returned must be "description"
    And the field "0.salary" returned must be "1000.0"
    And the field "0.job_type" returned must be "FULL_TIME"
    And the field "0.status" returned must be "PENDING"

  Scenario: Canceled jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
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
    And I call the update job post path with the following body
    """
      {
        "title": "job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "status": "CANCELED"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "0"

  Scenario: Filled jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
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
    And a post is created with
    """
      {
        "title": "Filled job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    And I call the update job post path with the following body
    """
      {
        "title": "Filled job post title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "status": "FILLED"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "1"
    And the field "0.id" returned must be "not null"
    And the field "0.title" returned must be "job post title"
    And the field "0.description" returned must be "description"
    And the field "0.salary" returned must be "1000.0"
    And the field "0.job_type" returned must be "FULL_TIME"
    And the field "0.status" returned must be "PENDING"

  Scenario: Filled jobs are not returned
    Given the job provider is logged in with username "username" and password "password"
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
    And I call the update job post path with the following body
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path
    Then the status returned must be 200
    And the array "" returned must have size "0"
