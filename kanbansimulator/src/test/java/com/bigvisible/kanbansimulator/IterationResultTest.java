package com.bigvisible.kanbansimulator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IterationResultTest {
    static final String[] WORKFLOW_STEP_NAMES = new String[] {"BA", "Dev", "WebDev", "QA"};
    static final String FIRST_WORKFLOW_STEP = WORKFLOW_STEP_NAMES[0];
    static final String LAST_WORKFLOW_STEP = WORKFLOW_STEP_NAMES[WORKFLOW_STEP_NAMES.length-1];

    public static class Capacity_settings_determine_how_much_work_is_completed_and_how_much_is_queued {
        private IterationResult iterationResult;
        private int batchSize;
        
        @Before
        public void given() {
            iterationResult = new IterationResult();
            batchSize = 20;  // some reasonable amount to allow for some work to become queued.

            iterationResult.setPutIntoPlay(batchSize);
        }
        
        @Test
        public void when_the_capacity_of_a_workflow_step_matches_batch_size_THEN_all_stories_are_completed_and_none_are_queued_up() {
            iterationResult.setCapacity(FIRST_WORKFLOW_STEP, batchSize);

            iterationResult.run();

            assertEquals(batchSize, iterationResult.getCompleted(FIRST_WORKFLOW_STEP));
            assertEquals(0, iterationResult.getQueued(FIRST_WORKFLOW_STEP));
        }

        @Test
        public void when_the_capacity_of_a_workflow_step_is_less_than_batch_size_then_some_stories_are_queued_up() throws Exception {
            int howMuchLessCapacityIsThanBatchSize = 5;
            int capacityOfBA = batchSize - howMuchLessCapacityIsThanBatchSize;

            iterationResult.setCapacity(FIRST_WORKFLOW_STEP, capacityOfBA);

            iterationResult.run();

            assertEquals(capacityOfBA, iterationResult.getCompleted(FIRST_WORKFLOW_STEP));
            assertEquals(howMuchLessCapacityIsThanBatchSize, iterationResult.getQueued(FIRST_WORKFLOW_STEP));
        }

        @Test
        public void when_qa_has_capacity_for_all_work_completed_by_webdev_then_that_work_is_completed()
                throws Exception {
            setCapacityToAllWorkflowStepsTo(iterationResult, batchSize);

            iterationResult.run();

            assertEquals(batchSize, iterationResult.getCompleted(LAST_WORKFLOW_STEP));
            assertEquals(0, iterationResult.getQueued(LAST_WORKFLOW_STEP));
            assertEquals(batchSize, iterationResult.getTotalCompleted());
        }

        private static void setCapacityToAllWorkflowStepsTo(IterationResult iterationResult, int capacity) {
            for (int idx = 0; idx < WORKFLOW_STEP_NAMES.length; idx++) {
                iterationResult.setCapacity(WORKFLOW_STEP_NAMES[idx], capacity);
            }
        }
    }
    
    public static class Results_from_one_iteration_affects_the_next_iteration {
        IterationResult iterationResult;

        @Before
        public void given() {
            iterationResult = new IterationResult();
        }

        @Test
        public void when_an_iteration_completes_the_next_iteration_number_is_one_greater() throws Exception {
            iterationResult.setIterationNumber(1);
            IterationResult nextIteration = iterationResult.nextIteration();
        
            assertEquals(2, nextIteration.getIterationNumber());
        }

        @Test
        public void when_an_iteration_completes_the_next_iteration_maintains_the_capacity_settings() throws Exception {
            int batchSize = 20;

            iterationResult.setPutIntoPlay(batchSize);
            iterationResult.setCapacity("BA", batchSize);
            iterationResult.setCapacity("Dev", batchSize);
            iterationResult.setCapacity("WebDev", batchSize);
            iterationResult.setCapacity("QA", batchSize);

            iterationResult.run();

            IterationResult nextIteration = iterationResult.nextIteration();

            assertEquals(iterationResult.getCapacity("BA"), nextIteration.getCapacity("BA"));
            assertEquals(iterationResult.getCapacity("Dev"), nextIteration.getCapacity("Dev"));
            assertEquals(iterationResult.getCapacity("WebDev"), nextIteration.getCapacity("WebDev"));
            assertEquals(iterationResult.getCapacity("QA"), nextIteration.getCapacity("QA"));
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
            iterationResult.setPutIntoPlay(batchSize);
            iterationResult.setCapacity("BA", batchSize-1);
            iterationResult.setCapacity("Dev", batchSize-2);
            iterationResult.setCapacity("WebDev", batchSize-3);
            iterationResult.setCapacity("QA", batchSize-4);

            iterationResult.run();

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
            firstIteration.setPutIntoPlay(1);

            firstIteration.run();

            IterationResult secondIteration = firstIteration.nextIteration();

            secondIteration.setPutIntoPlay(1);
            secondIteration.run();

            assertEquals(firstIteration.getTotalCompleted() + 1, secondIteration.getTotalCompleted());
        }

        @Test
        public void when_work_remains_from_previous_iteration_and_there_is_not_enough_capacity_the_queue_accumulates_the_work_not_started()
                throws Exception {
            IterationResult iteration = new IterationResult();
            iteration.setIterationNumber(1);
            iteration.setCapacity("BA", 1);
            iteration.setQueued("BA", 1);
            iteration.setPutIntoPlay(2);

            iteration.run();

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
