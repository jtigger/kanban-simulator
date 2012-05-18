Feature: Run a simple Simulation

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24541242>

	As a Kanban Class Student,
	I want to be able to run a simulation
	where:
		- there are four steps in the workflow,
		- there are 10 iterations in the simulation,
		- the capacity and batch size are as described in TOC_V3.xls, "Agile PMO (Just Say No)" tab.
	so that I can identify where the constraints are in the simulated workflow system and anticipate
	        when the work is going to be completed.

I know this is done when...
    Scenario: hard-coded configuration for Agile PMO
      Given we hard-code the Agile PMO simulation configuration
        And the batch size is 11 stories.
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Agile PMO tab of the spreadsheet); with column titles.

    Scenario: hard-coded configuration for Locally Optimized for BA
      Given we hard-code the Locally Optimized for BA simulation configuration
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Locally Optimized for BA tab of the spreadsheet); with column titles.