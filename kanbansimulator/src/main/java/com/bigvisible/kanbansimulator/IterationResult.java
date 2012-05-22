package com.bigvisible.kanbansimulator;

public class IterationResult {
	private int iterationNumber;
	private int putIntoPlay;
	private int capacityOfBA;
	private int completedByBA;
	private int remainingInBAQueue;
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

	public void run() {
		remainingInBAQueue += putIntoPlay;
		completedByBA = Math.min(remainingInBAQueue, capacityOfBA);
		remainingInBAQueue -= completedByBA;

		remainingInDevQueue += completedByBA; 
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
		nextIteration.setCapacityOfBA(capacityOfBA);
		nextIteration.setCapacityOfDev(capacityOfDev);
		nextIteration.setCapacityOfWebDev(capacityOfWebDev);
		nextIteration.setCapacityOfQA(capacityOfQA);
		nextIteration.remainingInBAQueue = remainingInBAQueue;
		nextIteration.remainingInDevQueue = remainingInDevQueue;
		nextIteration.remainingInWebDevQueue = remainingInWebDevQueue;
		nextIteration.remainingInQAQueue = remainingInQAQueue;
		nextIteration.totalCompleted = totalCompleted;
		
		return nextIteration;
	}

	@Override
	public String toString() {
		return "" + iterationNumber + "," + putIntoPlay + "," + capacityOfBA
				+ "," + completedByBA + "," + remainingInBAQueue;
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
		return capacityOfBA;
	}

	public void setCapacityOfBA(int capacityOfBA) {
		this.capacityOfBA = capacityOfBA;
	}

	public int getCompletedByBA() {
		return completedByBA;
	}

	public int getRemainingInBAQueue() {
		return remainingInBAQueue;
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
		this.completedByBA = completedByBA;
	}

	public void setRemainingInBAQueue(int remainingInBAQueue) {
		this.remainingInBAQueue = remainingInBAQueue;
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
