package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationParameter;

public class IterationParameterTest {

    @Test
    public void when_parameter_configures_capacity_THEN_it_is_a_workflowConfigurationParameter() {
        assertTrue(IterationParameter.startingAt(1).forStep("Dev").setCapacity(4).isWorkflowConfiguration());
    }
    
    @Test
    public void when_parameter_configures_batch_size_THEN_it_is_NOT_a_workflowConfigurationParameter() throws Exception {
        assertFalse(IterationParameter.startingAt(1).setBatchSize(1).isWorkflowConfiguration());
    }

}
