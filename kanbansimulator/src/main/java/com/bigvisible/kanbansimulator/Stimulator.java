package com.bigvisible.kanbansimulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Stimulator {
	
	private int batchSize = 11;
	private int businessAnalystCapacity;
	private int developmentCapacity;
	private int webDevelopmentCapacity;
	private int qualityAssuranceCapacity;

	public void run(File resultsFile) {
	    IterationResult firstIteration = new IterationResult();
	    firstIteration.setIterationNumber(1);
	    firstIteration.setPutIntoPlay(batchSize);
	    firstIteration.setCapacityOfBA(businessAnalystCapacity);
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

	public void setBusinessAnalystCapacity(int businessAnalystCapacity) {
		this.businessAnalystCapacity = businessAnalystCapacity;
	}

	public void setDevelopmentCapacity(int developmentCapacity) {
		this.developmentCapacity = developmentCapacity;
	}

	public void setWebDevelopmentCapacity(int webDevelopmentCapacity) {
		this.webDevelopmentCapacity = webDevelopmentCapacity;
	}

	public void setQualityAssuranceCapacity(int qualityAssuranceCapacity) {
		this.qualityAssuranceCapacity = qualityAssuranceCapacity;
	}

}
