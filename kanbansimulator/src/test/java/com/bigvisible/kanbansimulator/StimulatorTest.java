package com.bigvisible.kanbansimulator;

import static org.junit.Assert.*;

import org.junit.Test;

public class StimulatorTest {

	@Test
	public void simulator_runs_until_all_stories_are_finished() {
		Stimulator stimuator = new Stimulator();
		stimuator.addStories(88);
		stimuator.run();
		
		assertEquals(88, stimuator.getStoriesCompleted());
	}

}
