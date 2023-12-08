#language: en
#utf-8


# This Test has written by ruixiang wu - Student-Id: 239052677



Feature: Find jobs with filter
  Job seeker should be able to search for jobs
  and filter jobs base on types and title and category

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

  Scenario: search job with title and type and category returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"FULL_TIME",
        "title":"title",
        "category":"IT"
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with title returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"",
        "title":"title",
        "category":""
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with type returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"FULL_TIME",
        "title":"",
        "category":""
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with category returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"",
        "title":"",
        "category":"IT"
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with title and type returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"FULL_TIME",
        "title":"title",
        "category":""
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with title and category returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"",
        "title":"title",
        "category":"IT"
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: search job with type and category returned successfully
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true",
        "category":"IT",
        "deadline": "2023-12-31T23:00:00"
      }
    """
    And the job seeker is logged in with username "dfsd1f111566666" and password "testacpasswor"
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"FULL_TIME",
        "title":"",
        "category":"IT"
      }
    """
    Then the status returned must be 200
    And the field "content.0.title" returned must be "title"
    And the field "content.0.description" returned must be "description"
    And the field "content.0.salary" returned must be "1000.0"
    And the field "content.0.job_type" returned must be "FULL_TIME"
    And the field "content.0.status" returned must be "PENDING"
    And the field "content.0.category" returned must be "IT"
    And the field "content.0.deadline" returned must be "2023-12-31T23:00:00"
    And the field "content.0.id" returned must be "not null"

  Scenario: Seeker is unauthorized
    Given the job provider is logged in with username "username" and password "password"
    And a post is created with
    """
      {
        "title": "title",
        "description": "description",
        "salary": "1000.0",
        "job_type": "FULL_TIME",
        "is_visible": "true"
      }
    """
    And the header is empty
    When I call the search jobs path with condition with body
    """
      {
        "job_type":"FULL_TIME",
        "title":"title",
        "category":"IT"
      }
    """
    Then the status returned must be 401
