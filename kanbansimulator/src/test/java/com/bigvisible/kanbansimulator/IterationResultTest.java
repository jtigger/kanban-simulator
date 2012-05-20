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
	

}
