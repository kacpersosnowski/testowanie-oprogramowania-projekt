Feature: Retrieve task list
  Get all specified tasks

  Scenario: Get all tasks
    Given there are tasks in the db
    When I request to retrieve them
    Then it should retrieve all of them

  Scenario: Retrieve tasks chronologically
    Given there are tasks in the db
    When I request to retrieve them chronologically
    Then it should retrieve all of them arranged by ascending date

  Scenario: Retrieve uncompleted tasks
    Given there are uncompleted tasks in the db
    When I want to retrieve uncompleted tasks
    Then it should retrieve all uncompleted tasks

  Scenario: Retrieve completed tasks
    Given there are completed tasks in the db
    When I want to retrieve completed tasks
    Then it should retrieve all completed tasks

  Scenario: Retrieve tasks by priority
    Given there are tasks in the db with given priority
    When I request to retrieve them by priority
    Then it should retrieve tasks with given priority