package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;

/**
 * Specifies the default characteristics and behavior of a {@link SimulatorEngine}.
 */
public class OutOfTheBoxSimulatorSpec {

    @Test
    public void simulator_starts_with_all_capacities_set_to_one() throws Exception {
        Simulator simulator = new SimulatorEngine();

        simulator.setNumberOfIterationsToRun(1);
        simulator.run(null);
        
        assertEquals(1, simulator.results().get(0).getCapacity("BA"));
        assertEquals(1, simulator.results().get(0).getCapacity("Dev"));
        assertEquals(1, simulator.results().get(0).getCapacity("WebDev"));
        assertEquals(1, simulator.results().get(0).getCapacity("QA"));
    }

    @Test
    public void simulator_runs_until_all_stories_are_finished() {
        Simulator stimuator = new SimulatorEngine();
        stimuator.addStories(88);
        stimuator.run(null);

        assertEquals(88, stimuator.getStoriesCompleted());
    }
}
