Feature: Configure Batch Size Iteration By Iteration

	https://bigvisible.leankitkanban.com/Boards/View/19325506/24544456

	As a Kanban Class Instructor,
	I want to be able to configure batch sizes iteration by iteration
	so that I can demonstrate the increase in throughput of applying Drum-Buffer-Rope into a workflow

	Note: Testing of the GUI for the added Batch Size parameter is done in the GUI.feature file.
	
I know this is done when...

    Scenario: Apply the Rope based on reduced Web Dev capacity
      Given the backlog starts with 88 stories
        And the following workflow capacities and batch sizes by iteration:
        | Iteration | BA Capacity | Dev Capacity | Web Dev Capacity | QA Capacity | Batch Size |
      	|         1 |          13 |           12 |               12 |          10 | 		11 |
      	|         2 |          13 |           12 |                6 |          10 |			 7 |
      	|         3 |          13 |           12 |                6 |          10 |			 7 |
      	|         4 |          13 |           12 |                6 |          10 |			 7 |
      	|         5 |          13 |           12 |               12 |          10 |			11 |
      	|         6 |          13 |           12 |               12 |          10 |			11 |
      	|         7 |          13 |           12 |               12 |          10 |			11 |
      	|         8 |          13 |           12 |               12 |          10 |			11 |
      	|         9 |          13 |           12 |               12 |          10 |			11 |
      	|        10 |          13 |           12 |               12 |          10 |			11 |
      When the simulator completes a run
      Then the simulator will have generated the following results:
        | Iteration | Put in Play  | BA Capacity | BA Completed | BA Remaining in Queue | Dev Capacity | Dev Completed | Dev Remaining in Queue | Web Dev Capacity | Web Dev Completed | Web Dev Remaining in Queue | QA Capacity | QA Completed | QA Remaining in Queue | Total Completed |
        |         1 |          11  |          13 |           11 |                     0 |           12 |            11 |                      0 |               12 |                11 |                          0 |          10 |           10 |                     1 |              10 |         
        |         9 |          11  |          13 |           11 |                     0 |           12 |            11 |                      0 |               12 |                11 |                          0 |          10 |           10 |                     8 |              79 |         

