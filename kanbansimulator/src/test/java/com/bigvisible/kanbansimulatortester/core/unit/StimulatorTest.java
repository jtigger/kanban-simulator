package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bigvisible.kanbansimulator.InvalidSimulatorConfiguration;
import com.bigvisible.kanbansimulator.IterationParameter;
import com.bigvisible.kanbansimulator.IterationResult;
import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;

public class StimulatorTest {

    @Test
    public void simulator_runs_until_all_stories_are_finished() {
        Simulator stimuator = new SimulatorEngine();
        stimuator.addStories(88);
        stimuator.run(null);

        assertEquals(88, stimuator.getStoriesCompleted());
    }

    @Test
    public void when_capacity_is_modified_for_a_workflow_step_at_a_given_iteration_THEN_the_amount_of_work_completed_matches_that_modification_for_that_iteration()
            throws Exception {
        // how's that for a test method name?!?!?!?!

        Simulator stimulator = new SimulatorEngine();
        int initialBACapacity = 4;
        int greaterThanBACapacity = 10;
        int increaseInBACapacity = 2;

        stimulator.addStories(greaterThanBACapacity);
        stimulator.setBatchSize(greaterThanBACapacity);
        stimulator.setBusinessAnalystCapacity(initialBACapacity);
        stimulator.addParameter(IterationParameter.startingAt(2).forStep("BA")
                .setCapacity(initialBACapacity + increaseInBACapacity));

        stimulator.run(null);

        IterationResult firstIteration = stimulator.results().get(0);
        IterationResult secondIteration = stimulator.results().get(1);

        assertEquals("Increase in work completed should match increase in capacity.", increaseInBACapacity,
                secondIteration.getCompleted("BA") - firstIteration.getCompleted("BA"));
    }
    
    @Test(expected=InvalidSimulatorConfiguration.class)
    public void when_a_parameter_is_added_for_a_non_existent_workflow_step_THEN_that_configuration_is_rejected() throws Exception {
        Simulator stimulator = new SimulatorEngine();
        stimulator.addStories(1);
        
        stimulator.addParameter(IterationParameter.startingAt(1).forStep("InvalidWorkflowStepName").setCapacity(10));
        stimulator.run(null);
    }
}
