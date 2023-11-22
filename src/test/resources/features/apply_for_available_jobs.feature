#language: en
#utf-8

Feature: Apply for available jobs functionality
  Job seeker should be able to apply for available jobs
  To be able to be schedule interviews

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

  Scenario: Job is applied successfully
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
    When I call the apply for jobs path for the job "job post title"
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "job_post.title" returned must be "job post title"
    And the field "job_post.description" returned must be "description"
    And the field "job_post.salary" returned must be "1000.0"
    And the field "job_post.job_type" returned must be "FULL_TIME"
    And the field "job_post.status" returned must be "PENDING"
    And the field "applicant.username" returned must be "dfsd1f111566666"
    And the field "applicant.password" returned must be "null"
    And the field "applicant.nickname" returned must be "test_nickname"
    And the field "applicant.email" returned must be "test"
    And the field "applicant.phone" returned must be "444412345"
    And the field "applicant.firstname" returned must be "testirstname"
    And the field "applicant.lastname" returned must be "testlastname"
    And the field "applicant.gender" returned must be "male"

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
    When I call the apply for jobs path for the job "job post title"
    Then the status returned must be 401

  Scenario: Invisible jobs cannot be applied for
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
    When I call the apply for jobs path for the job "invisible job post title"
    Then the status returned must be 404
    And the field "error" returned must be "Job post not found"

  Scenario: Canceled jobs cannot be applied for
    Given the job provider is logged in with username "username" and password "password"
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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the apply for jobs path for the job "Canceled job post title"
    Then the status returned must be 404
    And the field "error" returned must be "Job post not found"

  Scenario: Filled jobs cannot be applied for
    Given the job provider is logged in with username "username" and password "password"
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
    When I call the apply for jobs path for the job "Filled job post title"
    Then the status returned must be 404
    And the field "error" returned must be "Job post not found"
