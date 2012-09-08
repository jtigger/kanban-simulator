package com.bigvisible.kanbansimulatortester.core;

import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;
import cucumber.runtime.PendingException;

public class ConfigureWorkflowSteps extends StepDefinitionForSimulatorSpecification {
    @Given("^I remove the workflow step named \"([^\"]*)\"$")
    public void I_remove_the_workflow_step_named(String arg1) {
        getStimulator().removeWorkflowStep("Web Dev");
    }
}
