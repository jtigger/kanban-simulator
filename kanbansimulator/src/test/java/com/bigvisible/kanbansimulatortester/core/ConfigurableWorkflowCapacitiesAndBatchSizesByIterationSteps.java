package com.bigvisible.kanbansimulatortester.core;

import static com.bigvisible.kanbansimulator.IterationParameter.startingAt;

import java.util.List;

import com.bigvisible.kanbansimulatortester.common.IterationParameterExample;
import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;
import cucumber.runtime.PendingException;

public class ConfigurableWorkflowCapacitiesAndBatchSizesByIterationSteps extends StepDefinitionForSimulatorSpecification {

	@Given("^the following workflow capacities and batch sizes by iteration:$")
	public void the_following_workflow_capacities_and_batch_sizes_by_iteration(List<IterationParameterExample> parameterExamples) {
        for (IterationParameterExample parameterExample : parameterExamples) {
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("BA").setCapacity(parameterExample.bACapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("Dev").setCapacity(parameterExample.devCapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("WebDev").setCapacity(
                            parameterExample.webDevCapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("QA").setCapacity(parameterExample.qACapacity));
        }

		
		
		// Express the Regexp above with the code you wish you had
	    // For automatic conversion, change DataTable to List<YourType>
	    throw new PendingException();
	}

}
