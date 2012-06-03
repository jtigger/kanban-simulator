package com.bigvisible.kanbansimulator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StimulatorTest {

    @Test
    public void simulator_runs_until_all_stories_are_finished() {
        Stimulator stimuator = new Stimulator();
        stimuator.addStories(88);
        stimuator.run(null);

        assertEquals(88, stimuator.getStoriesCompleted());
    }

    @Test
    public void when_capacity_is_modified_for_a_workflow_step_at_a_given_iteration_THEN_the_amount_of_work_completed_matches_that_modification_for_that_iteration()
            throws Exception {
        // how's that for a test method name?!?!?!?!

        Stimulator stimulator = new Stimulator();
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
        Stimulator stimulator = new Stimulator();
        stimulator.addStories(1);
        
        stimulator.addParameter(IterationParameter.startingAt(1).forStep("InvalidWorkflowStepName").setCapacity(10));
        stimulator.run(null);
    }
}
