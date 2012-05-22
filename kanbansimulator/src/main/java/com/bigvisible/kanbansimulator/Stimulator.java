package com.bigvisible.kanbansimulator;

import java.util.LinkedList;
import java.util.List;

public class Stimulator {
	private int totalStories = 0;
	private int storiesCompleted = 0;
	private int storiesUnplayed = 0;
	private int batchSize = 11;
	private int businessAnalystCapacity = 1;
	private int developmentCapacity = 1;
	private int webDevelopmentCapacity = 1;
	private int qualityAssuranceCapacity = 1;
	private List<IterationResult> results = new LinkedList<IterationResult>();

	public void run(/*File resultsFile */) {
		int iterationNumber = 1;
		storiesUnplayed = totalStories;
	    IterationResult iteration = new IterationResult();
	    iteration.setIterationNumber(iterationNumber);

		while(storiesCompleted < totalStories) {
		    iteration.setPutIntoPlay(Math.min(storiesUnplayed, batchSize));
		    iteration.setCapacityOfBA(businessAnalystCapacity);
		    iteration.setCapacityOfDev(developmentCapacity);
		    iteration.setCapacityOfWebDev(webDevelopmentCapacity);
		    iteration.setCapacityOfQA(qualityAssuranceCapacity);
		    iteration.run();
		    
		    results.add(iteration);
		    storiesUnplayed -= iteration.getPutIntoPlay();
		    storiesCompleted = iteration.getTotalCompleted();
		    iteration = iteration.nextIteration();
		}
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

	public void addStories(int storiesToAdd) {
		totalStories += storiesToAdd;
		storiesUnplayed += storiesToAdd;
	}

	public int getStoriesCompleted() {
		return storiesCompleted;
	}

}
