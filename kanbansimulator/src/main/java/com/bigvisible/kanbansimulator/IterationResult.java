package com.bigvisible.kanbansimulator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IterationResult {
	private int iterationNumber;
	private int putIntoPlay;
	private int totalCompleted;
	
	private List<WorkflowStep> steps;

	private static class WorkflowStep {
		private String description;
		private int capacity;
		private int completed;
		private int queued;
		
		public WorkflowStep(String description) {
			setDescription(description);
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getCapacity() {
			return capacity;
		}
		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}
		public int getCompleted() {
			return completed;
		}
		public void setCompleted(int completed) {
			this.completed = completed;
		}
		public int getQueued() {
			return queued;
		}
		public void setQueued(int queued) {
			this.queued = queued;
		}
	}
	
	public IterationResult() {
		steps = new LinkedList<WorkflowStep>();
		steps.add(new WorkflowStep("BA"));
		steps.add(new WorkflowStep("Dev"));
		steps.add(new WorkflowStep("WebDev"));
		steps.add(new WorkflowStep("QA"));
	}
	
	private WorkflowStep getStep(String description) {
		for (WorkflowStep step : steps) {
			if(step.getDescription().equalsIgnoreCase(description)) {
				return step;
			}
		}
		return null;
	}
	
	public void run() {
		WorkflowStep ba = getStep("BA");
		ba.setQueued(ba.getQueued()+putIntoPlay);
		ba.setCompleted(Math.min(ba.getQueued(), ba.getCapacity()));
		ba.setQueued(ba.getQueued() - ba.getCompleted());

		WorkflowStep dev = getStep("Dev");
		dev.setQueued(dev.getQueued()+ba.getCompleted());
		dev.setCompleted(Math.min(dev.getQueued(), dev.getCapacity()));
		dev.setQueued(dev.getQueued() - dev.getCompleted());
		
		WorkflowStep webDev = getStep("WebDev");
		webDev.setQueued(webDev.getQueued()+dev.getCompleted());
		webDev.setCompleted(Math.min(webDev.getQueued(), webDev.getCapacity()));
		webDev.setQueued(webDev.getQueued() - webDev.getCompleted());
		
		WorkflowStep qa = getStep("QA");
		qa.setQueued(qa.getQueued()+webDev.getCompleted());
		qa.setCompleted(Math.min(qa.getQueued(), qa.getCapacity()));
		qa.setQueued(qa.getQueued() - qa.getCompleted());

		totalCompleted += qa.getCompleted();
	}

	public IterationResult nextIteration() {
		IterationResult nextIteration = new IterationResult();
		nextIteration.iterationNumber = iterationNumber+1;
		nextIteration.setCapacityOfBA(getStep("BA").getCapacity());
		nextIteration.setCapacityOfDev(getStep("Dev").getCapacity());
		nextIteration.setCapacityOfWebDev(getStep("WebDev").getCapacity());
		nextIteration.setCapacityOfQA(getStep("QA").getCapacity());
		nextIteration.setRemainingInBAQueue(getStep("BA").getQueued());
		nextIteration.getStep("Dev").setQueued(getStep("Dev").getQueued());
		nextIteration.getStep("WebDev").setQueued(getStep("WebDev").getQueued());
		nextIteration.getStep("QA").setQueued(getStep("QA").getQueued());
		nextIteration.totalCompleted = totalCompleted;
		
		return nextIteration;
	}

	public int getIterationNumber() {
		return iterationNumber;
	}

	public void setIterationNumber(int iterationNumber) {
		this.iterationNumber = iterationNumber;
	}

	public int getPutIntoPlay() {
		return putIntoPlay;
	}

	public void setPutIntoPlay(int putIntoPlay) {
		this.putIntoPlay = putIntoPlay;
	}

	public int getCapacityOfBA() {
		return getStep("BA").getCapacity();
	}

	public void setCapacityOfBA(int capacityOfBA) {
		getStep("BA").setCapacity(capacityOfBA);
	}

	public int getCompletedByBA() {
		return getStep("BA").getCompleted();
	}

	public int getRemainingInBAQueue() {
		return getStep("BA").getQueued();
	}

	public int getCapacityOfDev() {
		return getStep("Dev").getCapacity();
	}

	public void setCapacityOfDev(int capacityOfDev) {
		getStep("Dev").setCapacity(capacityOfDev);
	}

	public int getCompletedByDev() {
		return getStep("Dev").getCompleted();
	}

	public int getRemainingInDevQueue() {
		return getStep("Dev").getQueued();
	}

	public int getCapacityOfWebDev() {
		return getStep("WebDev").getCapacity();
	}

	public int getCapacityOfQA() {
		return getStep("QA").getCapacity();
	}

	public void setCompletedByBA(int completedByBA) {
		getStep("BA").setCompleted(completedByBA);
	}

	public void setRemainingInBAQueue(int remainingInBAQueue) {
		getStep("BA").setQueued(remainingInBAQueue);
	}

	public void setCompletedByDev(int completedByDev) {
		getStep("Dev").setCompleted(completedByDev);
	}

	public void setRemainingInDevQueue(int remainingInDevQueue) {
		getStep("Dev").setQueued(remainingInDevQueue);
	}

	public void setCompletedByWebDev(int completedByWebDev) {
		getStep("WebDev").setCompleted(completedByWebDev);
	}

	public void setRemainingInWebDevQueue(int remainingInWebDevQueue) {
		getStep("WebDev").setQueued(remainingInWebDevQueue);
	}

	public void setCompletedByQA(int completedByQA) {
		getStep("QA").setCompleted(completedByQA);
	}

	public void setRemainingInQAQueue(int remainingInQAQueue) {
		getStep("QA").setQueued(remainingInQAQueue);
	}

	public void setCapacityOfWebDev(int capacityOfWebDev) {
		getStep("WebDev").setCapacity(capacityOfWebDev);
	}

	public int getCompletedByWebDev() {
		return getStep("WebDev").getCompleted();
	}

	public int getRemainingInWebDevQueue() {
		return getStep("WebDev").getQueued();
	}

	public void setCapacityOfQA(int capacityOfQA) {
		getStep("QA").setCapacity(capacityOfQA);
	}

	public int getCompletedByQA() {
		return getStep("QA").getCompleted();
	}

	public int getRemainingInQAQueue() {
		return getStep("QA").getQueued();
	}

	public int getTotalCompleted() {
		return totalCompleted;
	}
}
