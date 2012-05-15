Feature: Configurable Batch Size

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24544454>

	As a Kanban Class Student,
	I want to be able to run a simulation
	where:
		- the batch size is configurable
		- capacity limits are set for each of the 4 steps in the workflow as defined in the TOC_V3.xls, "Locally Optimized for BA"
		- there are 10 iterations in the simulation
	so that I can see how the batch size effects the flow of stories through the workflow 
		when there are capacity limits on each step in the workflow
	
I know this is done when...
	Scenario: configurable batch size for locally optimized BA	
    	Given the configuration lives in a text file
    	When I specify a batch size of 13 in the configuration file
    	Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the spreadsheet); with column titles.

	Scenario: configurable batch size for Agile PMO	
    	Given the configuration lives in a text file
    	When I specify a batch size of 11 in the configuration file
    	Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the spreadsheet); with column titles.
    	