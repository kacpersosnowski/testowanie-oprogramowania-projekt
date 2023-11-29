Feature: Show proportion of completed tasks
  Show what percentage of tasks i have completed

  Scenario: Show the statistic
    Given there are tasks in the db
    When i want to see the statistic
    Then it should return it
