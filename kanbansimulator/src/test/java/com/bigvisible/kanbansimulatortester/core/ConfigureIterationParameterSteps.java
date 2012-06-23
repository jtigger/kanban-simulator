package com.bigvisible.kanbansimulatortester.core;

import static com.bigvisible.kanbansimulator.IterationParameter.startingAt;

import java.util.List;

import com.bigvisible.kanbansimulatortester.common.IterationParameterExample;
import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.When;
import cucumber.runtime.PendingException;

public class ConfigureIterationParameterSteps extends StepDefinitionForSimulatorSpecification {

    @Given("^the following workflow capacities by iteration:$")
    public void the_following_workflow_capacities_by_iteration(List<IterationParameterExample> parameterExamples) {
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
    }
    
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

    
    @Given("^the workflow capacities come from a file with the following values:$")
    public void the_workflow_capacities_come_from_a_file_with_the_following_values(String arg1) {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @When("^the simulator completes a run using a file for iteration capacity parameters$")
    public void the_simulator_completes_a_run_using_a_file_for_iteration_capacity_parameters() {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

}
