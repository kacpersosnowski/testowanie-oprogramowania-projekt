Feature: Find task in db
  Find your task in the db
  Scenario: Task found successfully by ID
    Given the desired task exists in the db
    When i want to look it up
    Then it should be found
  Scenario: Task not found by ID
    Given the desired task does not exist in the db
    When i want to look it up
    Then returning a task should return an error
  Scenario: Task found successfully by title
    Given the desired task exists in the db
    When i want to find it by title
    Then it should return all tasks containing the desired title
  Scenario: Task not found by title
    Given the desired task does not exist in the db
    When i want to find it by title
    Then it should return null

