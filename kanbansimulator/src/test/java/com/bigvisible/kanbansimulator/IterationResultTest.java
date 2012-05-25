package com.bigvisible.kanbansimulator;

import static org.junit.Assert.*;

import org.junit.Test;

public class IterationResultTest {

	@Test
	public void when_BA_capacity_matches_batch_size_then_all_stories_are_played() {
		IterationResult iterationResult = new IterationResult();
		int batchSize = 10;
		
		iterationResult.setPutIntoPlay(batchSize);
		iterationResult.setCapacity("BA", batchSize);
		
		iterationResult.run();
		
		assertEquals(batchSize, iterationResult.getCompleted("BA"));
		assertEquals(0, iterationResult.getQueued("BA"));
	}
	
	@Test
	public void when_BA_capacity_is_less_than_batch_size_then_some_stories_are_queued_up() throws Exception {
		IterationResult iterationResult = new IterationResult();
		int batchSize = 15;
		int capacityOfBA = 10;
		int howMuchLessCapacityIsThanBatchSize = batchSize - capacityOfBA;
		
		iterationResult.setPutIntoPlay(batchSize);
		iterationResult.setCapacity("BA", capacityOfBA);
		
		iterationResult.run();
		
		assertEquals(capacityOfBA, iterationResult.getCompleted("BA"));
		assertEquals(howMuchLessCapacityIsThanBatchSize, iterationResult.getQueued("BA"));
	}
	
	@Test
	public void when_dev_has_capacity_for_all_work_completed_by_BA_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacity("BA", 10);
		iterationResult.setCapacity("Dev", 10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompleted("Dev"));
		assertEquals(0, iterationResult.getQueued("Dev"));
	}

	@Test
	public void when_webdev_has_capacity_for_all_work_completed_by_dev_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacity("BA", 10);
		iterationResult.setCapacity("Dev", 10);
		iterationResult.setCapacity("WebDev", 10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompleted("WebDev"));
		assertEquals(0, iterationResult.getQueued("WebDev"));
	}

	@Test
	public void when_qa_has_capacity_for_all_work_completed_by_webdev_then_that_work_is_completed() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacity("BA", 10);
		iterationResult.setCapacity("Dev", 10);
		iterationResult.setCapacity("WebDev", 10);
		iterationResult.setCapacity("QA", 10);
		
		iterationResult.run();
		
		assertEquals(10, iterationResult.getCompleted("QA"));
		assertEquals(0, iterationResult.getQueued("QA"));
		assertEquals(10, iterationResult.getTotalCompleted());
	}
	
    @Test
	public void when_an_iteration_completes_the_next_iteration_maintains_the_capacity_settings() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacity("BA", 10);
		iterationResult.setCapacity("Dev", 10);
		iterationResult.setCapacity("WebDev", 10);
		iterationResult.setCapacity("QA", 10);
		
		iterationResult.run();
		
		IterationResult nextIteration = iterationResult.nextIteration();
		
		assertEquals(iterationResult.getCapacity("BA"), nextIteration.getCapacity("BA"));
		assertEquals(iterationResult.getCapacity("Dev"), nextIteration.getCapacity("Dev"));
		assertEquals(iterationResult.getCapacity("WebDev"), nextIteration.getCapacity("WebDev"));
		assertEquals(iterationResult.getCapacity("QA"), nextIteration.getCapacity("QA"));
	}
    
    @Test
	public void when_an_iteration_completes_the_next_iteration_carries_over_queue_amounts() throws Exception {
		IterationResult iterationResult = new IterationResult();
		
		// configured so that a story is queued in each workflow step
		iterationResult.setPutIntoPlay(10);
		iterationResult.setCapacity("BA", 9);
		iterationResult.setCapacity("Dev", 8);
		iterationResult.setCapacity("WebDev", 7);
		iterationResult.setCapacity("QA", 6);
		
		iterationResult.run();
		
		IterationResult nextIteration = iterationResult.nextIteration();
		
		assertEquals(1, nextIteration.getQueued("BA"));
		assertEquals(1, nextIteration.getQueued("Dev"));
		assertEquals(1, nextIteration.getQueued("WebDev"));
		assertEquals(1, nextIteration.getQueued("QA"));
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
    	firstIteration.setCapacity("BA", 1);
    	firstIteration.setCapacity("Dev", 1);
    	firstIteration.setCapacity("WebDev", 1);
    	firstIteration.setCapacity("QA", 1);
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
    	iteration.setCapacity("BA", 1);
    	iteration.setQueued("BA", 1);
    	iteration.setPutIntoPlay(2);
    	
    	iteration.run();

    	assertEquals(2, iteration.getQueued("BA"));
	}
    
    public static class SerializationToAndFromCSV {
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
