#language: en
#utf-8


# This Test has written by ruixiang wu - Student-Id: 239052677



Feature: Receive feedback from seeker feature
  Job provider must be able to receive feedbacks from seeker

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
    And the job seeker posts the feedback with following body
    """
      {
        "feedback": "This company is nice, friendly and professional."
      }
    """
    When I call the receive feedback from seeker path
    Then the status returned must be 200
    And the field "id" returned must be "not null"
    And the field "provider_feedbacks" returned must be "[]"
    And the field "seeker_feedbacks" returned must be '["This company is nice, friendly and professional."]'

