#language: en
#utf-8

Feature: Job provider send feedback feature
  Job provider must be able to post feedbacks
  So job seeker can see them later

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
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    And I call the apply for jobs path for the job "job post title"

  Scenario: Job is applied successfully
    Given the job provider is logged in with username "username" and password "password"
    When the job provider posts the following feedback to the application
    """
      {
        "feedback": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      }
    """
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "applicant.username" returned must be "dfsd1f111566666"
    And the field "applicant.password" returned must be "null"
    And the field "applicant.nickname" returned must be "test_nickname"
    And the field "applicant.email" returned must be "test"
    And the field "applicant.phone" returned must be "444412345"
    And the field "applicant.firstname" returned must be "testirstname"
    And the field "applicant.lastname" returned must be "testlastname"
    And the field "applicant.gender" returned must be "male"
    And the field "provider_feedbacks.0" returned must be "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    And the field "seeker_feedbacks" returned must be "[]"

  Scenario: Seeker is unauthorized
    Given the header is empty
    When the job provider posts the following feedback to the application
    """
      {
        "feedback": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      }
    """
    Then the status returned must be 401
