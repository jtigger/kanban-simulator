Feature: Configurable number of Iterations

    As a Kanban Class Instructor,
    I want to be able to configure the number of iterations that the simulator will run
    so that I can ...
    

I know this is done when...

    Background: (This background defines what is a 'simple' simulation)
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

  #  Scenario: keep going even if all stories are complete
    
  #  Scenario: stop when there are no more stories in any workflow step
        