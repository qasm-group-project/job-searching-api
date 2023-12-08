#language: en
#utf-8


# This Test has written by Luis Brienze - Student-Id: 239060399



Feature: Verify retrieval of job posts in csv file

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

  Scenario: Retrieving job posts in csv file
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
        "title": "job post title2",
        "description": "description2",
        "salary": "100.0",
        "job_type": "PART_TIME",
        "is_visible": "true"
      }
    """
    When I call get all provider job posts by csv file
    Then the status returned must be 200
    And the csv field "0.title" returned must be "job post title"
    And the csv field "0.description" returned must be "description"
    And the csv field "0.salary" returned must be "1000.0"
    And the csv field "0.job_type" returned must be "FULL_TIME"
    And the csv field "0.is_visible" returned must be "true"
    And the csv field "0.number_of_applicants" returned must be "0"
    And the csv field "1.title" returned must be "job post title2"
    And the csv field "1.description" returned must be "description2"
    And the csv field "1.salary" returned must be "100.0"
    And the csv field "1.job_type" returned must be "PART_TIME"
    And the csv field "1.is_visible" returned must be "true"
    And the csv field "1.number_of_applicants" returned must be "0"
