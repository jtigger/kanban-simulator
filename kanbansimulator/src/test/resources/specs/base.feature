Feature: Run a simple Simulation

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24541242>

	As a Kanban Class Instructor,
	I want to be able to configure the batch size for a simulation
	so that I can identify where the constraints are in the simulated workflow system and anticipate
	when the work is going to be completed.

I know this is done when...

    Background:
    Given the workflow has 4 steps
      And the BA capacity is 13 stories per iteration
      And the Dev capacity is 12 stories per iteration
      And the Web Dev capacity is 12 stories per iteration
      And the QA capacity is 10 stories per iteration

    Scenario: Agile PMO (Just Say No!)
      Given the batch size is 11 stories
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Agile PMO tab of the spreadsheet); with column titles

    Scenario: Locally Optimized for BA
      Given we hard-code the Locally Optimized for BA simulation configuration
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Locally Optimized for BA tab of the spreadsheet); with column titles