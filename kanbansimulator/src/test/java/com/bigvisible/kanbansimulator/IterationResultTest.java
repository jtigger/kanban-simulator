package com.bigvisible.kanbansimulator;

import static org.junit.Assert.*;

import org.junit.Test;

public class IterationResultTest {

	@Test
	public void when_BA_capacity_matches_batch_size_then_all_stories_are_played() {
		IterationResult iterationResult = new IterationResult();
		int batchSize = 10;
		
		iterationResult.setPutIntoPlay(batchSize);
		iterationResult.setCapacityOfBA(batchSize);
		
		iterationResult.run();
		
		assertEquals(batchSize, iterationResult.getCompletedByBA());
		assertEquals(0, iterationResult.getRemainingInBAQueue());
	}
	
	@Test
	public void when_BA_capacity_is_less_than_batch_size_then_some_stories_are_queued_up() throws Exception {
		IterationResult iterationResult = new IterationResult();
		int batchSize = 15;
		int capacityOfBA = 10;
		int howMuchLessCapacityIsThanBatchSize = batchSize - capacityOfBA;
		
		iterationResult.setPutIntoPlay(batchSize);
		iterationResult.setCapacityOfBA(capacityOfBA);
		
		iterationResult.run();
		
		assertEquals(capacityOfBA, iterationResult.getCompletedByBA());
		assertEquals(howMuchLessCapacityIsThanBatchSize, iterationResult.getRemainingInBAQueue());
	}
	
	@Test
	public void when_dev_has_capacity_for_all_work_completed_by_BA_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacityOfBA(10);
		iterationResult.setCapacityOfDev(10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompletedByDev());
		assertEquals(0, iterationResult.getRemainingInDevQueue());
	}

	@Test
	public void when_webdev_has_capacity_for_all_work_completed_by_dev_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacityOfBA(10);
		iterationResult.setCapacityOfDev(10);
		iterationResult.setCapacityOfWebDev(10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompletedByWebDev());
		assertEquals(0, iterationResult.getRemainingInWebDevQueue());
	}

	@Test
	public void when_qa_has_capacity_for_all_work_completed_by_webdev_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacityOfBA(10);
		iterationResult.setCapacityOfDev(10);
		iterationResult.setCapacityOfWebDev(10);
		iterationResult.setCapacityOfQA(10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompletedByQA());
		assertEquals(0, iterationResult.getRemainingInQAQueue());
		assertEquals(10, iterationResult.getTotalCompleted());
	}
	
    @Test
	public void when_an_iteration_completes_the_next_iteration_maintains_the_capacity_settings() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacityOfBA(10);
		iterationResult.setCapacityOfDev(10);
		iterationResult.setCapacityOfWebDev(10);
		iterationResult.setCapacityOfQA(10);
		
		iterationResult.run();
		
		IterationResult nextIteration = iterationResult.nextIteration();
		
		assertEquals(iterationResult.getCapacityOfBA(), nextIteration.getCapacityOfBA());
		assertEquals(iterationResult.getCapacityOfDev(), nextIteration.getCapacityOfDev());
		assertEquals(iterationResult.getCapacityOfWebDev(), nextIteration.getCapacityOfWebDev());
		assertEquals(iterationResult.getCapacityOfQA(), nextIteration.getCapacityOfQA());
	}
    
    @Test
	public void when_an_iteration_completes_the_next_iteration_carries_over_queue_amounts() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		// configured so that a story is queued in each workflow step
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacityOfBA(9);
		iterationResult.setCapacityOfDev(8);
		iterationResult.setCapacityOfWebDev(7);
		iterationResult.setCapacityOfQA(6);
		
		iterationResult.run();
		
		IterationResult nextIteration = iterationResult.nextIteration();
		
		assertEquals(1, nextIteration.getRemainingInBAQueue());
		assertEquals(1, nextIteration.getRemainingInDevQueue());
		assertEquals(1, nextIteration.getRemainingInWebDevQueue());
		assertEquals(1, nextIteration.getRemainingInQAQueue());
	}
    
    @Test
	public void when_an_iteration_completes_the_next_iteration_number_is_one_greater() throws Exception {
    	IterationResult iteration = new IterationResult();
    	iteration.setIterationNumber(1);
    	IterationResult nextIteration = iteration.nextIteration();
    	
    	assertEquals(2, nextIteration.getIterationNumber());
	}
    
    @Test
	public void when_an_iteration_completes_more_work_it_is_additive() throws Exception {
    	IterationResult firstIteration = new IterationResult();
    	firstIteration.setIterationNumber(1);
    	firstIteration.setCapacityOfBA(1);
    	firstIteration.setCapacityOfDev(1);
    	firstIteration.setCapacityOfWebDev(1);
    	firstIteration.setCapacityOfQA(1);
    	firstIteration.setPutIntoPlay(1);
    	
    	firstIteration.run();

    	IterationResult secondIteration = firstIteration.nextIteration();
    	
    	secondIteration.setPutIntoPlay(1);
    	secondIteration.run();
    	
    	assertEquals(firstIteration.getTotalCompleted()+1, secondIteration.getTotalCompleted());
	}
    
    @Test
	public void when_work_remains_from_previous_iteration_and_there_is_not_enough_capacity_the_queue_accumulates_the_work_not_started() throws Exception {
    	IterationResult iteration = new IterationResult();
    	iteration.setIterationNumber(1);
    	iteration.setCapacityOfBA(1);
    	iteration.setRemainingInBAQueue(1);
    	iteration.setPutIntoPlay(2);
    	
    	iteration.run();

    	assertEquals(2, iteration.getRemainingInBAQueue());
	}
}
