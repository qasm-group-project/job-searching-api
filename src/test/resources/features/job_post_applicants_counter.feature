#language: en
#utf-8

Feature: Verify retrieval of job posts with number of applicants
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
        "username":"testusername",
        "password":"testacpasswor",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
      }
    """
  Scenario: Retrieving job posts with number of applicants
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
    Then the job seeker is logged in with username "testusername" and password "testacpasswor"
    And I call the apply for jobs path for the job "job post title"
    # update token for provider
    And the job provider is logged in with username "username" and password "password"
    When I call get all provider job posts
    """
      {
      }
    """
    Then the status returned must be 200
    And the field "content.0.numberOfApplicants" returned must be "1"

