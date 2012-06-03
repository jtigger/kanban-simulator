package com.bigvisible.kanbansimulator;

import static org.junit.Assert.*;

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
    
    public void when_capacity_is_modified_for_a_workflow_step_at_a_given_iteration_THEN_that_change_applies_for_subsequent_iterations() {
    }
    public void when_capacity_is_modified_for_a_workflow_step_at_a_given_iteration_THEN_that_change_does_NOT_apply_for_previous_iterations() {
    }
    public void when_capacity_is_modified_for_a_workflow_step_at_multiple_iterations_THEN_that_increase_does_NOT_apply_for_previous_iterations() {
    }
    
}
