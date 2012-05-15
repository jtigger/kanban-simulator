Feature: Configurable Batch Size

    <https://bigvisible.leankitkanban.com/Boards/View/19325506/24544454>

	As a Kanban Class Student,
	I want to be able to run a simulation
	where:
		- the batch size is configurable
		- capacity limits and iterations are hard-coded as defined in the TOC_V3.xls, "Locally Optimized for BA"
	so that I can control the batch size to see how the batch size effects the flow of stories through the workflow 
		when there are capacity limits on each step in the workflow
	
I know this is done when...
	Scenario: configurable batch size for locally optimized BA	
    	Given the configuration lives in a text file
    	When I get the batch size from the configuration
    	Then it generates a .csv file containing the iteration-by-iteration results (exactly as seen in the Locally Optimized for BA tab of the sspreadsheet); with column titles.

"""
TODO Question for John: 
Do we want to test for a specific result in this scenario or do we just want
to test that we can open a file and get some results?
"""    	