Feature: Create a task.
	Creating a new task for the future.
Scenario: Everything is correct
	Given everything is ok
	When I add a new task
	Then the task should be saved to the db
