package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationResult;

public class IterationResultTest {
    static final String[] WORKFLOW_STEP_NAMES = new String[] { "BA", "Dev", "WebDev", "QA" };
    static final String FIRST_WORKFLOW_STEP = WORKFLOW_STEP_NAMES[0];
    static final String LAST_WORKFLOW_STEP = WORKFLOW_STEP_NAMES[WORKFLOW_STEP_NAMES.length - 1];

    public static class When_a_typical_iteration_is_run {
        private IterationResult iterationResult;
        private int batchSize;

        @Before
        public void given_a_typical_iteration() {
            iterationResult = new IterationResult();
            batchSize = UnitTestHelper.anyReasonableNumber();

            iterationResult.setBatchSize(batchSize);
        }

        @Test
        public void given_the_capacity_of_a_workflow_step_matches_batch_size__then_all_stories_are_completed_and_none_are_queued_up() {
            iterationResult.setCapacity(FIRST_WORKFLOW_STEP, batchSize);

            iterationResult.run(batchSize);

            assertEquals(batchSize, iterationResult.getCompleted(FIRST_WORKFLOW_STEP));
            assertEquals(0, iterationResult.getQueued(FIRST_WORKFLOW_STEP));
        }

        @Test
        public void given_the_capacity_of_a_workflow_step_is_less_than_batch_size__then_some_stories_are_queued_up()
                throws Exception {
            int howMuchLessCapacityIsThanBatchSize = 5;
            int capacityOfBA = batchSize - howMuchLessCapacityIsThanBatchSize;

            iterationResult.setCapacity(FIRST_WORKFLOW_STEP, capacityOfBA);

            iterationResult.run(batchSize);

            assertEquals(capacityOfBA, iterationResult.getCompleted(FIRST_WORKFLOW_STEP));
            assertEquals(howMuchLessCapacityIsThanBatchSize, iterationResult.getQueued(FIRST_WORKFLOW_STEP));
        }

        @Test
        public void given_the_capacity_for_all_workflow_steps_is_greater_than_the_batch_size__then_all_stories_are_completed_in_the_iteration()
                throws Exception {
            for (String workflowStepName : WORKFLOW_STEP_NAMES) {
                iterationResult.setCapacity(workflowStepName, batchSize);
            }

            iterationResult.run(batchSize);

            assertEquals(batchSize, iterationResult.getCompleted(LAST_WORKFLOW_STEP));
            assertEquals(0, iterationResult.getQueued(LAST_WORKFLOW_STEP));
            assertEquals(batchSize, iterationResult.getTotalCompleted());
        }
        
        @Test
        public void given_the_batch_size_is_less_than_available_stories_to_play_THEN_the_number_of_stories_put_into_play_matches_the_batch_size() throws Exception {
            int storiesAvailableToPlay = batchSize+1;
            iterationResult.run(storiesAvailableToPlay);
            assertEquals(batchSize, iterationResult.getPutIntoPlay());
        }
        
        @Test
        public void given_the_batch_size_is_greater_than_available_stories_to_play_THEN_the_number_of_stories_put_into_play_matches_available_stories() throws Exception {
            int storiesAvailableToPlay = batchSize-1;
            iterationResult.run(storiesAvailableToPlay);
            assertEquals(storiesAvailableToPlay, iterationResult.getPutIntoPlay());
        }
    }

    public static class When_the_next_iteration_is_generated {
        IterationResult iterationResult;

        @Before
        public void given() {
            iterationResult = new IterationResult();
        }

        @Test
        public void its_iteration_number_is_one_more_than_the_previous() throws Exception {
            int iterationNumber = UnitTestHelper.anyReasonableNumber();
            iterationResult.setIterationNumber(iterationNumber);
            IterationResult nextIteration = iterationResult.nextIteration();

            assertEquals(iterationNumber + 1, nextIteration.getIterationNumber());
        }

        @Test
        public void its_capacity_settings_match_those_of_the_previous() throws Exception {
            for (String workflowStepName : WORKFLOW_STEP_NAMES) {
                iterationResult.setCapacity(workflowStepName, UnitTestHelper.anyReasonableNumber());
            }

            iterationResult.run(0);

            final IterationResult nextIteration = iterationResult.nextIteration();

            for (String workflowStepName : WORKFLOW_STEP_NAMES) {
                assertEquals(iterationResult.getCapacity(workflowStepName),
                        nextIteration.getCapacity(workflowStepName));
            }
        }
        
        @Test
        public void its_batch_size_matches_that_of_the_previous() throws Exception {
            int batchSize = 10;
            iterationResult.setBatchSize(batchSize);

            IterationResult nextIteration = iterationResult.nextIteration();
            
            assertEquals(batchSize, nextIteration.getBatchSize());
        }
    }

    public static class Uncategorized {
        IterationResult iterationResult;

        @Before
        public void given() {
            iterationResult = new IterationResult();
        }

        @Test
        public void when_an_iteration_completes_the_next_iteration_carries_over_queue_amounts() throws Exception {
            int batchSize = 20;

            // configured so that a story is queued in each workflow step
            iterationResult.setBatchSize(batchSize);
            iterationResult.setCapacity("BA", batchSize - 1);
            iterationResult.setCapacity("Dev", batchSize - 2);
            iterationResult.setCapacity("WebDev", batchSize - 3);
            iterationResult.setCapacity("QA", batchSize - 4);

            iterationResult.run(batchSize);

            IterationResult nextIteration = iterationResult.nextIteration();

            assertEquals(1, nextIteration.getQueued("BA"));
            assertEquals(1, nextIteration.getQueued("Dev"));
            assertEquals(1, nextIteration.getQueued("WebDev"));
            assertEquals(1, nextIteration.getQueued("QA"));
        }

        @Test
        public void when_an_iteration_completes_more_work_it_is_additive() throws Exception {
            IterationResult firstIteration = new IterationResult();
            firstIteration.setIterationNumber(1);
            firstIteration.setCapacity("BA", 1);
            firstIteration.setCapacity("Dev", 1);
            firstIteration.setCapacity("WebDev", 1);
            firstIteration.setCapacity("QA", 1);
            firstIteration.setBatchSize(1);

            firstIteration.run(1);

            IterationResult secondIteration = firstIteration.nextIteration();

            secondIteration.setBatchSize(1);
            secondIteration.run(1);

            assertEquals(firstIteration.getTotalCompleted() + 1, secondIteration.getTotalCompleted());
        }

        @Test
        public void when_work_remains_from_previous_iteration_and_there_is_not_enough_capacity_the_queue_accumulates_the_work_not_started()
                throws Exception {
            IterationResult iteration = new IterationResult();
            iteration.setIterationNumber(1);
            iteration.setCapacity("BA", 1);
            iteration.setQueued("BA", 1);
            iteration.setBatchSize(2);

            iteration.run(2);

            assertEquals(2, iteration.getQueued("BA"));
        }
    }

    public static class Iteration_Results_can_be_serialized_to_and_from_CSV {
        @Test
        public void can_serialize_to_CSV() throws Exception {
            IterationResult iteration = new IterationResult();
            iteration.setIterationNumber(0);
            iteration.setPutIntoPlay(1);
            iteration.setCapacity("BA", 2);
            iteration.setCompleted("BA", 3);
            iteration.setQueued("BA", 4);
            iteration.setCapacity("Dev", 5);
            iteration.setCompleted("Dev", 6);
            iteration.setQueued("Dev", 7);
            iteration.setCapacity("WebDev", 8);
            iteration.setCompleted("WebDev", 9);
            iteration.setQueued("WebDev", 10);
            iteration.setCapacity("QA", 11);
            iteration.setCompleted("QA", 12);
            iteration.setQueued("QA", 13);

            String asCSV = iteration.toCSVString();

            assertEquals("0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0", asCSV);
        }

        @Test
        public void can_deserialize_from_CSV() throws Exception {
            String asCSV = "0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14";

            IterationResult iteration = IterationResult.parseCSV(asCSV);

            assertEquals(0, iteration.getIterationNumber());
            assertEquals(1, iteration.getPutIntoPlay());
            assertEquals(2, iteration.getCapacity("BA"));
            assertEquals(3, iteration.getCompleted("BA"));
            assertEquals(4, iteration.getQueued("BA"));
            assertEquals(5, iteration.getCapacity("Dev"));
            assertEquals(6, iteration.getCompleted("Dev"));
            assertEquals(7, iteration.getQueued("Dev"));
            assertEquals(8, iteration.getCapacity("WebDev"));
            assertEquals(9, iteration.getCompleted("WebDev"));
            assertEquals(10, iteration.getQueued("WebDev"));
            assertEquals(11, iteration.getCapacity("QA"));
            assertEquals(12, iteration.getCompleted("QA"));
            assertEquals(13, iteration.getQueued("QA"));
            assertEquals(14, iteration.getTotalCompleted());
        }
    }
}
