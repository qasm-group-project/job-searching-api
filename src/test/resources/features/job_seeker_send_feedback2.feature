#language: en
#utf-8
  
Feature: Job seeker send feedback feature
  job seeker must be able to post feedbacks
  so job provider can see them later
  
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

  Scenario: Feedback is send by the seeker
    Given the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When the job seeker posts the feedback with following body
    """
       {
         "feedback": "This company is nice, friendly and professional."
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
    And the field "provider_feedbacks" returned must be "[]"
    And the field "seeker_feedbacks.0" returned must be "This company is nice, friendly and professional."