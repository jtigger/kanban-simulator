package com.bigvisible.kanbansimulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Stimulator {
	
	private int batchSize = 11;

	public void run(File resultsFile) {
	    int capacityOfBA = 13;
	    
	    IterationResult firstIteration = new IterationResult();
	    firstIteration.setIterationNumber(1);
	    firstIteration.setPutIntoPlay(batchSize);
	    firstIteration.setCapacityOfBA(capacityOfBA);
	    firstIteration.run();
		
		try {
			FileWriter file = new FileWriter(resultsFile);
			file.write(firstIteration.toString());
			file.flush();
			file.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
