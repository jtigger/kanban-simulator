package com.bigvisible.kanbansimulatortester.core.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.bigvisible.kanbansimulator.IterationResult;

public class IterationResultSerializationSpec {
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