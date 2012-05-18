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

"""
TODO Question for John:
If we leave the details of the batch size to the step definitions then I as the PO have to go to the step 
definitions to see how my BDD test is going to behave. Is this ok? 

If we parameterize them within the When clause then I can see the expected behavior from with the feature file.  

If we do parameterize in the scenario which would be most appropriate?
    Scenario: hard-coded configuration for Agile PMO
      Given we hard-code the Agile PMO simulation configuration
      When the simulator completes a run using a batch size of 13
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Agile PMO tab of the spreadsheet); with column titles.
---or ---
    Scenario: hard-coded configuration for Agile PMO
      Given we hard-code the Agile PMO simulation configuration and use a batch size of 13
      When the simulator completes a run
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Agile PMO tab of the spreadsheet); with column titles.
---or ---
    Scenario: hard-coded configuration for Agile PMO
      Given we hard-code the Agile PMO simulation configuration
      When the simulator completes a run
	  And we use a batch size of 13
      Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Agile PMO tab of the spreadsheet); with column titles.

"""    