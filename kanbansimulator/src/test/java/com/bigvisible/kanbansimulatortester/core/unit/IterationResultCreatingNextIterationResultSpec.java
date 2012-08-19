package com.bigvisible.kanbansimulatortester.core.unit;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.fest.assertions.Fail;
import org.junit.Before;
import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationResult;

/**
 * Specifies how a {@link IterationResult} creates and initializes "the next" {@link IterationResult}.
 */
public class IterationResultCreatingNextIterationResultSpec {

    private IterationResult iterationResult;

    @Before
    public void GIVEN_an_IterationResult() {
        iterationResult = new IterationResult();
    }

    @Test
    public void WHEN_a_IterationResult_creates_the_next_iteration_THEN_the_new_IterationResults_IterationNumber_is_one_greater()
            throws Exception {
        int iterationNumber = UnitTestHelper.anyReasonableNumber();
        iterationResult.setIterationNumber(iterationNumber);

        IterationResult nextIteration = iterationResult.nextIteration();

        assertThat("New IterationResult's IterationNumber should be one greater than the previous.",
                nextIteration.getIterationNumber(), is(iterationNumber + 1));
    }

    @Test
    public void WHEN_a_IterationResult_creates_the_next_iteration_THEN_the_new_IterationResults_workflow_step_capacities_match_the_capacities_of_previous()
            throws Exception {
        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            iterationResult.setCapacity(workflowStepName, UnitTestHelper.anyReasonableNumber());
        }

        IterationResult nextIteration = iterationResult.nextIteration();
        
        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            assertThat("Capacity for \"" + workflowStepName + "\" should be the same.",
                    nextIteration.getCapacity(workflowStepName), is(iterationResult.getCapacity(workflowStepName)));
        }
    }

    @Test
    public void WHEN_a_IterationResult_creates_the_next_iteration_THEN_the_new_IterationResults_batch_size_matches_that_of_the_previous()
            throws Exception {
        int batchSize = UnitTestHelper.anyReasonableNumber();
        iterationResult.setBatchSize(batchSize);

        IterationResult nextIteration = iterationResult.nextIteration();

        assertThat(nextIteration.getBatchSize(), is(batchSize));
    }

    @Test
    public void WHEN_a_IterationResult_creates_the_next_iteration_THEN_the_new_IterationResults_workflow_queues_match_that_of_the_previous()
            throws Exception {
        int batchSize = UnitTestHelper.anyReasonableNumber();

        iterationResult.setBatchSize(batchSize);
        
        int storiesAvailableToPlay = batchSize;
        // configure workflow step capacities so that exactly one story will queue at each step
        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            iterationResult.setCapacity(workflowStepName, --storiesAvailableToPlay);
        }

        iterationResult.run(batchSize);

        IterationResult nextIteration = iterationResult.nextIteration();

        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            assertThat(nextIteration.getQueued(workflowStepName), is(1));            
        }
    }
    
    @Test
    public void totalCompleted_is_a_running_total_and_not_just_how_many_stories_completed_in_the_iteration() throws Exception {
        iterationResult.setBatchSize(1);
        for (String workflowStepName : IterationResultSpecHelper.WORKFLOW_STEP_NAMES) {
            iterationResult.setCapacity(workflowStepName, iterationResult.getBatchSize());
        }
        iterationResult.run(1);

        IterationResult nextIteration = iterationResult.nextIteration();
        nextIteration.run(1);

        assertThat(nextIteration.getTotalCompleted(), is(iterationResult.getTotalCompleted() + 1));
    }

}