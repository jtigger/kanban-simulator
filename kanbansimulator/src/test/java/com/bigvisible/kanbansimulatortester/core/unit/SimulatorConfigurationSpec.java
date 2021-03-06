package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static com.bigvisible.kanbansimulator.IterationParameter.WorkflowStepParameter.*;
import static com.bigvisible.kanbansimulator.IterationParameter.*;

import org.junit.Test;

import com.bigvisible.kanbansimulator.InvalidSimulatorConfiguration;
import com.bigvisible.kanbansimulator.IterationResult;
import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;

/**
 * Specifies how the Simulator accepts and applies configuration.
 * 
 * For details about how configuration propagates from one iteration to the next, see {@link IterationParameterSpec}.
 */
public class SimulatorConfigurationSpec {

    @Test
    public void WHEN_configuration_is_specified_for_a_specific_iteration_THEN_the_simulator_applies_that_configuration_starting_at_that_iteration_and_no_sooner() throws Exception {
        Simulator simulator = new SimulatorEngine();

        int defaultBACapacity = 1;
        int configuredBACapacity = 5;
        simulator.addParameter(startingAt(3).forStep(named("BA").setCapacity(configuredBACapacity)));
        simulator.addStories(3); // at least enough to get to iteration 3
        
        simulator.run(null);

        assertThat(simulator.results().get(0).getCapacity("BA"), is(defaultBACapacity));
        assertThat(simulator.results().get(1).getCapacity("BA"), is(defaultBACapacity));
        assertThat(simulator.results().get(2).getCapacity("BA"), is(configuredBACapacity));
    }
    
    @Test
    public void WHEN_multiple_configuration_is_specified_for_the_same_iteration_THEN_the_simulator_applies_all_of_them() throws Exception {
        Simulator stimulator = new SimulatorEngine();

        stimulator.addParameter(startingAt(1).forStep(named("BA").setCapacity(2)));
        stimulator.addParameter(startingAt(1).forStep(named("Dev").setCapacity(2)));

        stimulator.setNumberOfIterationsToRun(1);
        stimulator.run(null);
        
        assertEquals(2, stimulator.results().get(0).getCapacity("BA"));
        assertEquals(2, stimulator.results().get(0).getCapacity("Dev"));
    }
    
    
    @Test(expected=InvalidSimulatorConfiguration.class)
    public void WHEN_configuration_is_specified_for_a_workflow_step_that_does_not_exist_THEN_the_simulator_rejects_that_configuration() throws Exception {
        Simulator stimulator = new SimulatorEngine();
        stimulator.addStories(1);
        
        stimulator.addParameter(startingAt(1).forStep(named("InvalidWorkflowStepName").setCapacity(10)));
        stimulator.run(null);
    }
            
    @Test
    public void WHEN_batch_size_is_configured_to_be_null_THEN_that_configuration_is_ignored() throws Exception {
        Simulator stimulator = new SimulatorEngine();
        stimulator.setNumberOfIterationsToRun(2);
        stimulator.addParameter(startingAt(1).setBatchSize(10));
        stimulator.addParameter(startingAt(2).setBatchSize(null));
        
        stimulator.run(null);
        
        IterationResult secondIteration = stimulator.results().get(1);
        
        assertEquals(10, secondIteration.getBatchSize());

    }
}
