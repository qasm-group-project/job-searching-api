#language: en
#utf-8

Feature: save jobs for seekers

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
    And the job seeker is created with
    """
      {
        "username":"usernametestseeker",
        "password":"passwordtestseeker",
        "nickname":"test_nickname",
        "email":"test",
        "phone":"444412345",
        "firstname":"testirstname",
        "lastname":"testlastname",
        "gender":"male"
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

  Scenario: Job is saved successfully
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"

  Scenario: Job is saved unsuccessfully - invalid post id
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job with invalid jobPosId
    Then the status returned must be 403
    And the field "message" returned must be "Job post not found"

  Scenario: Job is saved unsuccessfully - repeated request
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"
    When I call save post jobs path for the job
    Then the status returned must be 403
    And the field "message" returned must be "Job Post already saved!"

  Scenario: Saved job is deleted successfully
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"
    When I call the delete saved jobs path
    Then the status returned must be 200

  Scenario: Saved job is deleted unsuccessfully - invalid save post id
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"
    When I call the delete saved jobs path with invalid saved job id
    Then the status returned must be 403
    Then the field "message" returned must be "Saved Job post not found"

  Scenario: Saved job is deleted unsuccessfully - invalid seeker id
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"
    And the job seeker is logged in with username "usernametestseeker" and password "passwordtestseeker"
    When I call the delete saved jobs path
    Then the status returned must be 403
    Then the field "message" returned must be "You are not authorized to delete this saved job post"

  Scenario: get all saved job successfully
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call save post jobs path for the job
    Then the status returned must be 201
    And the field "message" returned must be "Job Post Saved successfully!"
    When I call get all saved post jobs path
    Then the status returned must be 200
    Then the field "totalElements" returned must be "1"
