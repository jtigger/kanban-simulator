Feature: Configurabe workflow capacities for each iteration

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24544456>

	As a Kanban Class Instructor,
	I want to be able to configure the capacities for each workflow step iteration by iteration
	so that I can demonstrate the effects of a local and temporary change of one workflow step's capacity  
	on the overall throughput of the system.

I know this is done when...

    Background: (This background defines the constants in the simulation)
    Given the backlog starts with 88 stories
      And the batch size is 11 stories

    Scenario: Original Beer Game presentation scenario
      Given the following workflow capacities by iteration:
        | Iteration | BA Capacity | Dev Capacity | Web Dev Capacity | QA Capacity |
      	|         1 |          13 |           12 |               12 |          10 |
      	|         2 |          13 |           12 |                6 |          10 |
      	|         3 |          13 |           12 |                6 |          10 |
      	|         4 |          13 |           12 |                6 |          10 |
      	|         5 |          13 |           12 |               18 |          10 |
      	|         6 |          13 |           12 |               18 |          10 |
      	|         7 |          13 |            8 |               12 |           8 |
      	|         8 |          13 |            8 |               12 |           8 |
      	|         9 |          13 |            8 |               12 |           8 |
      	|        10 |          13 |            8 |               12 |          10 |
      When the simulator completes a run
      Then the simulator will have generated the following results:
        | Iteration | Put in Play  | BA Capacity | BA Completed | BA Remaining in Queue | Dev Capacity | Dev Completed | Dev Remaining in Queue | Web Dev Capacity | Web Dev Completed | Web Dev Remaining in Queue | QA Capacity | QA Completed | QA Remaining in Queue | Total Completed |
        |         1 |          11  |          13 |           11 |                     0 |           12 |            11 |                      0 |               12 |                11 |                          0 |          10 |           10 |                     1 |              10 |         
        |        10 |           0  |          13 |            0 |                     0 |            8 |             0 |                      0 |               12 |                 0 |                          0 |          10 |           10 |                     5 |              83 |         
