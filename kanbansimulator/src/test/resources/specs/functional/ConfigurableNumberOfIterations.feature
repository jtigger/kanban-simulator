Feature: Configurable number of Iterations

    As a Kanban Class Instructor,
    I want to be able to configure the number of iterations that the simulator will run
    so that I can create a set of CFDs that map out the same number of iterations and
    have them have the same number of iterations in the x-axis in order to make the CFDs
    easy to compare to each other.
    
I know this is done when...

    Background: (This background defines a simulation that requires 9 iterations to complete)
    Given the backlog starts with 88 stories
      And the BA capacity is 13 stories per iteration
      And the Dev capacity is 12 stories per iteration
      And the Web Dev capacity is 12 stories per iteration
      And the QA capacity is 10 stories per iteration
      And the batch size is 11 stories

    Scenario: stop before all stories are done
      Given I set the number of iterations to 5
      When the simulator completes a run
      Then the simulator will have generated exactly 5 iterations 

    Scenario: keep going even if all stories are complete
      Given I set the number of iterations to 10
      When the simulator completes a run
      Then the simulator will have generated exactly 10 iterations 
    
    Scenario: stop when there are no more stories in any workflow step
      Given I set the number of iterations to 0
      When the simulator completes a run
      Then the simulator will have generated exactly 9 iterations 
        