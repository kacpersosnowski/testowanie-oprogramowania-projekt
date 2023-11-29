Feature: Toggle done status of task
  Mark a task as completed

  Scenario: Successful toggle to done
    Given task exists in the db
    And is not yet completed
    When I want to mark it as done
    Then it should be found
    And its status changed to done

  Scenario: Successful toggle to not done
    Given task exists in the db
    And is marked as completed
    When I want to mark it as not complete
    Then it should be found
    And its status changed to not done
