package com.bigvisible.kanbansimulator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IterationResult {
	private int iterationNumber;
	private int putIntoPlay;
	private int capacityOfDev;
	private int completedByDev;
	private int remainingInDevQueue;
	private int capacityOfWebDev;
	private int completedByWebDev;
	private int remainingInWebDevQueue;
	private int capacityOfQA;
	private int completedByQA;
	private int remainingInQAQueue;
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

		remainingInDevQueue += ba.getCompleted(); 
		completedByDev = Math.min(remainingInDevQueue, capacityOfDev);
		remainingInDevQueue -= completedByDev;

		remainingInWebDevQueue += completedByDev;
		completedByWebDev = Math.min(remainingInWebDevQueue, capacityOfWebDev);
		remainingInWebDevQueue -= completedByWebDev;

		remainingInQAQueue += completedByWebDev;
		completedByQA = Math.min(remainingInQAQueue, capacityOfQA);
		remainingInQAQueue -= completedByQA;
		
		totalCompleted += completedByQA;
	}

	public IterationResult nextIteration() {
		IterationResult nextIteration = new IterationResult();
		nextIteration.iterationNumber = iterationNumber+1;
		nextIteration.setCapacityOfBA(getStep("BA").getCapacity());
		nextIteration.setCapacityOfDev(capacityOfDev);
		nextIteration.setCapacityOfWebDev(capacityOfWebDev);
		nextIteration.setCapacityOfQA(capacityOfQA);
		nextIteration.setRemainingInBAQueue(getStep("BA").getQueued());
		nextIteration.remainingInDevQueue = remainingInDevQueue;
		nextIteration.remainingInWebDevQueue = remainingInWebDevQueue;
		nextIteration.remainingInQAQueue = remainingInQAQueue;
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
		return capacityOfDev;
	}

	public void setCapacityOfDev(int capacityOfDev) {
		this.capacityOfDev = capacityOfDev;
	}

	public int getCompletedByDev() {
		return completedByDev;
	}

	public int getRemainingInDevQueue() {
		return remainingInDevQueue;
	}

	public int getCapacityOfWebDev() {
		return capacityOfWebDev;
	}

	public int getCapacityOfQA() {
		return capacityOfQA;
	}

	public void setCompletedByBA(int completedByBA) {
		getStep("BA").setCompleted(completedByBA);
	}

	public void setRemainingInBAQueue(int remainingInBAQueue) {
		getStep("BA").setQueued(remainingInBAQueue);
	}

	public void setCompletedByDev(int completedByDev) {
		this.completedByDev = completedByDev;
	}

	public void setRemainingInDevQueue(int remainingInDevQueue) {
		this.remainingInDevQueue = remainingInDevQueue;
	}

	public void setCompletedByWebDev(int completedByWebDev) {
		this.completedByWebDev = completedByWebDev;
	}

	public void setRemainingInWebDevQueue(int remainingInWebDevQueue) {
		this.remainingInWebDevQueue = remainingInWebDevQueue;
	}

	public void setCompletedByQA(int completedByQA) {
		this.completedByQA = completedByQA;
	}

	public void setRemainingInQAQueue(int remainingInQAQueue) {
		this.remainingInQAQueue = remainingInQAQueue;
	}

	public void setCapacityOfWebDev(int capacityOfWebDev) {
		this.capacityOfWebDev = capacityOfWebDev;
	}

	public int getCompletedByWebDev() {
		return completedByWebDev;
	}

	public int getRemainingInWebDevQueue() {
		return remainingInWebDevQueue;
	}

	public void setCapacityOfQA(int capacityOfQA) {
		this.capacityOfQA = capacityOfQA;
	}

	public int getCompletedByQA() {
		return completedByQA;
	}

	public int getRemainingInQAQueue() {
		return remainingInQAQueue;
	}

	public int getTotalCompleted() {
		return totalCompleted;
	}
}
