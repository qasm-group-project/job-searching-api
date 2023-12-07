#language: en
#utf-8

Feature: Verify retrieval of job applications in csv file

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
    And a post is created with
    """
      {
        "title": "job post title2",
        "description": "description2",
        "salary": "100.0",
        "job_type": "PART_TIME",
        "is_visible": "true"
      }
    """
    And a post is created with
    """
      {
        "title": "job post title3",
        "description": "description3",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
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

  Scenario: Retrieving job posts in csv file
    Given the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    And I call the apply for jobs path for the job "job post title2"
    And I call the apply for jobs path for the job "job post title3"
    When I call get all seeker job applications by csv file
    Then the status returned must be 200
    And the csv field "0.title" returned must be "job post title2"
    And the csv field "0.description" returned must be "description2"
    And the csv field "0.salary" returned must be "100.0"
    And the csv field "0.job_type" returned must be "PART_TIME"
    And the csv field "0.is_visible" returned must be "true"
    And the csv field "0.number_of_applicants" returned must be "1"
    And the csv field "1.title" returned must be "job post title3"
    And the csv field "1.description" returned must be "description3"
    And the csv field "1.salary" returned must be "1000.0"
    And the csv field "1.job_type" returned must be "FULL_TIME"
    And the csv field "1.is_visible" returned must be "true"
    And the csv field "1.number_of_applicants" returned must be "1"
