Feature: Update a task.
  Updating a task for the future.
  Scenario: Everything is correct
    Given I want to update task
    And the task exists in the db
    When I update a valid task
    Then the task should be updated in the db
  Scenario: Name is empty
    Given I want to update task
    And the tasks title is empty
    When I update a invalid task
    Then the invalid task should not be found in the db
  Scenario: Date is already past
    Given I want to update task
    And the tasks date has already past
    When I update a invalid task
    Then the invalid task should not be found in the db
  Scenario: ID not found
    Given I want to update task
    And task is not found in db
    When I update a invalid task
    Then the invalid task should not be found in the db