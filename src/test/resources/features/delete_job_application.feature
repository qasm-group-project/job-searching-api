#language: en
#utf-8


# This Test has written by Nuntipat Pantarag - Student-Id: 239010006



Feature: Deleting Job Application for a Seeker
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
    And the job seeker is created with
    """
      {
        "username":"username",
        "password":"password",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
  Scenario: Job Seeker delete job application successfully!
    When I call the apply for jobs path for the job "job post title"
    And I call the delete job application with the following body
    """
      {
      }
    """
    Then the status returned must be 200