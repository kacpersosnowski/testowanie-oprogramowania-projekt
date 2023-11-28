Feature: Create a task.
	Creating a new task for the future.
Scenario: Everything is correct
	Given everything is ok
	When I add a new valid task
	Then the task should be saved to the db
Scenario: Name is empty
	Given the tasks title is empty
	When I add a new invalid task
	Then saving should return an error
	And not be saved to the db
Scenario: Date is already past
	Given the tasks date has already past
	When I add a new invalid task
	Then saving should return an error
And not be saved to the db


