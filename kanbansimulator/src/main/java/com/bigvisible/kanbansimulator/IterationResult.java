package com.bigvisible.kanbansimulator;

public class IterationResult {
	private int iterationNumber;
	private int putIntoPlay;
	private int capacityOfBA;
	private int completedByBA;
	private int remainingInBAQueue;
	
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

	@Override
	public String toString() {
		return ""+iterationNumber+","+putIntoPlay+","+capacityOfBA+","+completedByBA+","+remainingInBAQueue;
	}
	public void run() {
		completedByBA = Math.min(putIntoPlay, capacityOfBA);
		remainingInBAQueue = putIntoPlay-completedByBA;
	}
}
