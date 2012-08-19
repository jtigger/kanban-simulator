package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationResult;

/**
 * Specifies how an {@link IterationResult} simulates the execution of an single iteration.
 */
public class IterationResultSimulatesRunningAnIterationSpec {
    private IterationResult iterationResult;
    private int batchSize;

    @Before
    public void GIVEN_a_typical_iteration() {
        iterationResult = new IterationResult();
        batchSize = UnitTestHelper.anyReasonableNumber();

        iterationResult.setBatchSize(batchSize);
    }

    @Test
    public void GIVEN_the_capacity_of_a_workflow_step_matches_batch_size_WHEN_the_iteration_is_run_THEN_all_stories_are_completed_and_none_are_queued_up() {
        String someWorkflowStep = IterationResultSpecHelper.FIRST_WORKFLOW_STEP;
        iterationResult.setCapacity(someWorkflowStep, batchSize);

        iterationResult.run(batchSize);

        assertThat(iterationResult.getCompleted(someWorkflowStep), is(batchSize));
        assertThat(iterationResult.getQueued(someWorkflowStep), is(0));
    }

    @Test
    public void GIVEN_the_capacity_of_a_workflow_step_is_less_than_batch_size_WHEN_the_iteration_is_run_THEN_some_stories_are_queued_up()
            throws Exception {
        String someWorkflowStep = IterationResultSpecHelper.FIRST_WORKFLOW_STEP;
        int expectedNumberOfQueuedStories = 5;
        int capacity = batchSize - expectedNumberOfQueuedStories;
        
        iterationResult.setCapacity(someWorkflowStep, capacity);

        iterationResult.run(batchSize);

        assertThat(iterationResult.getCompleted(someWorkflowStep), is(capacity));
        assertThat(iterationResult.getQueued(someWorkflowStep), is(expectedNumberOfQueuedStories));
    }

    @Test
    public void GIVEN_the_capacity_for_all_workflow_steps_is_greater_than_the_batch_size_WHEN_the_iteration_is_run_THEN_all_stories_are_completed_in_the_iteration()
            throws Exception {
        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            iterationResult.setCapacity(workflowStepName, batchSize);
        }

        iterationResult.run(batchSize);

        assertThat(iterationResult.getTotalCompleted(), is(batchSize));
    }

    @Test
    public void GIVEN_the_batch_size_is_less_than_available_stories_to_play_WHEN_the_iteration_is_run_THEN_the_number_of_stories_put_into_play_matches_the_batch_size()
            throws Exception {
        int storiesAvailableToPlay = batchSize + 1;
        iterationResult.run(storiesAvailableToPlay);
        assertThat(iterationResult.getPutIntoPlay(), is(batchSize));
    }

    @Test
    public void GIVEN_the_batch_size_is_greater_than_available_stories_to_play_WHEN_the_iteration_is_run_THEN_the_number_of_stories_put_into_play_matches_available_stories()
            throws Exception {
        int storiesAvailableToPlay = batchSize - 1;
        iterationResult.run(storiesAvailableToPlay);
        assertThat(iterationResult.getPutIntoPlay(), is(storiesAvailableToPlay));
    }

    @Test
    public void GIVEN_the_iteration_has_stories_queued_in_a_workflow_step_AND_capacity_of_that_step_is_less_than_stories_available_to_play_WHEN_the_iteration_is_run_THEN_the_queue_increases()
            throws Exception {
        String someWorkflowStep = IterationResultSpecHelper.FIRST_WORKFLOW_STEP;
        int storiesQueuedBeforeRun = UnitTestHelper.anyReasonableNumber();
        int storiesAvailableToPlay = batchSize;
        int capacity = storiesAvailableToPlay - 1;
        int expectedStoriesQueuedAfterRun = storiesQueuedBeforeRun + 1;

        iterationResult.setIterationNumber(1);
        iterationResult.setCapacity(someWorkflowStep, capacity);
        iterationResult.setQueued(someWorkflowStep, storiesQueuedBeforeRun);

        iterationResult.run(storiesAvailableToPlay);

        assertThat(iterationResult.getQueued(someWorkflowStep), is(expectedStoriesQueuedAfterRun));
    }
    

}