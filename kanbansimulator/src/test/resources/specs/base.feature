Feature: Run a simple Simulation

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24541242>

	As a Kanban Class Student,
	I want to be able to run a simulation
	where:
		- there are four steps in the workflow,
		- there are 10 iterations in the simulation,
		- the capacity and batch size are as described in TOC_V2.xls, "Agile PMO (Just Say No)" tab.
	so that I can identify where the constraints are in the simulated workflow system and anticipate when the work is going to be completed.

I know this is done when...
    Scenario: hard-coded configuration
      Given we hard-code the simulation configuration
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the spreadsheet); with column titles.

"""           
    Scenario: 
       given 
       when I copy that data into the CFD spreadsheet,
       then the CFD itself is identical to what is seen in the TOC_V2.xls, "Agile PMO (Just Say No)".
"""