Feature: delete a task
	Delete an already existing task
Scenario: Everything is correct
	Given I want to delete a task
    And the task exists in the db
	When I want to delete the existing task
	Then it should be deleted from the db
Scenario: ID not found in db
    Given I want to delete a task
	And the task does not exist in the db
	When I want to delete the nonexistant task
	Then the invalid task should not be found in the db
