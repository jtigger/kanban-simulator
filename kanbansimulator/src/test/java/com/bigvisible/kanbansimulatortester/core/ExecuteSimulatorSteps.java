package com.bigvisible.kanbansimulatortester.core;

import java.io.IOException;

import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.When;

public class ExecuteSimulatorSteps extends StepDefinitionForSimulatorSpecification {
    @When("^the simulator completes a run$")
    public void the_simulator_completes_a_run() {
        getStimulator().run(getResultsOutput());
        if (getResultsOutput() != null) {
            try {
                getResultsOutput().close();
            } catch (IOException e) {
                throw new RuntimeException(
                        "While attempting to flush and close \""
                                + getResultsFile().getAbsolutePath() + "\"", e);
            }
        }
    }

}
