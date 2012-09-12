package com.bigvisible.kanbansimulatortester.core;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import com.bigvisible.kanbansimulatortester.common.IterationResultExample;
import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Then;
import cucumber.runtime.PendingException;

public class VerifyResultsOfTheSimulationSteps extends StepDefinitionForSimulatorSpecification {
    @Then("^the simulator will have generated the following results:$")
    public void the_simulator_will_have_generated_the_following_results(
            List<IterationResultExample> results) {
        List<IterationResultExample> actualResults = IterationResultExample
                .asExample(getStimulator().results());

        for (IterationResultExample iterationResultExample : results) {
            assertThat("... while looking for an example in the list of actual results ... (i.e. swap actual and expected in this message)",
                    iterationResultExample, isIn(actualResults));
        }
    }
    
    @Then("^the results do not contain values for \"([^\"]*)\"$")
    public void the_results_do_not_contain_values_for(String workflowStepName) {
        assertThat(workflowStepName, not(isIn(getStimulator().results().get(0).workflowStepNames())));
    }
    
    @Then("^the results do contain values for \"([^\"]*)\"$")
    public void the_results_do_contain_values_for(String workflowStepNamesCSV) {
        final String onCommasAndTrimmingSurroundingSpaces = " *, *";
        
        String[] workflowStepNames = workflowStepNamesCSV.split(onCommasAndTrimmingSurroundingSpaces);
        for (String workflowStepName : workflowStepNames) {
            assertThat(workflowStepName, isIn(getStimulator().results().get(0).workflowStepNames()));
        }
    }

}
