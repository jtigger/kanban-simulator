package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.*;
import static com.bigvisible.kanbansimulator.IterationParameter.WorkflowStepParameter.*;
import static com.bigvisible.kanbansimulator.IterationParameter.*;

import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationParameter;

public class IterationParameterSpec {

    @Test
    public void WHEN_parameter_configures_capacity_THEN_it_is_a_workflowConfigurationParameter() {
        assertTrue(IterationParameter.startingAt(1).forStep(named("Dev").setCapacity(4)).hasWorkflowConfiguration());
    }
    
    @Test
    public void WHEN_parameter_configures_batch_size_THEN_it_is_NOT_a_workflowConfigurationParameter() throws Exception {
        assertFalse(IterationParameter.startingAt(1).setBatchSize(1).hasWorkflowConfiguration());
    }
    
    @Test
    public void WHEN_user_configures_multiple_capacities_on_the_same_iteration_THEN_the_parameter_contains_all_of_that_configuration() throws Exception {
        IterationParameter iterationParameter = startingAt(1)
                .forStep(named("First Step").setCapacity(1))
                .forStep(named("Second Step").setCapacity(2));

        assertNotNull("Expected to find parameter for \"First Step\" in IterationParameter, but not found.", iterationParameter.getParameterForStep("First Step"));
        assertNotNull("Expected to find parameter for \"Second Step\" in IterationParameter, but not found.",iterationParameter.getParameterForStep("Second Step"));
    }
}
