package com.bigvisible.kanbansimulator;

import java.util.LinkedList;
import java.util.List;

public class Stimulator {
	
	private int batchSize = 11;
	private int businessAnalystCapacity;
	private int developmentCapacity;
	private int webDevelopmentCapacity;
	private int qualityAssuranceCapacity;
	private List<IterationResult> results = new LinkedList<IterationResult>();

	public void run(/*File resultsFile */) {
	    IterationResult firstIteration = new IterationResult();
	    firstIteration.setIterationNumber(1);
	    firstIteration.setPutIntoPlay(batchSize);
	    firstIteration.setCapacityOfBA(businessAnalystCapacity);
	    firstIteration.setCapacityOfDev(developmentCapacity);
	    firstIteration.setCapacityOfWebDev(webDevelopmentCapacity);
	    firstIteration.setCapacityOfQA(qualityAssuranceCapacity);
	    firstIteration.run();
	    
	    results.add(firstIteration);
		/*
		try {
			FileWriter file = new FileWriter(resultsFile);
			file.write(firstIteration.toString());
			file.flush();
			file.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		*/
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

	public List<IterationResult> results() {
		return results;
	}

}
