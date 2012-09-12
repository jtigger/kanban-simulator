Feature: Remove Workflow steps

    In order to be able to illustrate how consolidation of work and generalization of skill increases productivity,
    As a Kanban Class Instructor,
    I want to be able to remove a workflow step from the simulation before it begins
    (and by modifying the capacities of the remaining workflow steps, I can show that productivity increase compared
     to the default configuration).

I know this is done when...

    Background: (This background defines the constants in the simulation)
    Given the backlog starts with 88 stories
      
    Scenario: Removing a workflow step without adjusting capacity
      Given I remove the workflow step named "WebDev"
      When the simulator completes a run
      Then the results do not contain values for "WebDev"
       But the results do contain values for "BA, Dev, QA"

# TBD...     
#    Scenario: Increased Productivity Illustrated
