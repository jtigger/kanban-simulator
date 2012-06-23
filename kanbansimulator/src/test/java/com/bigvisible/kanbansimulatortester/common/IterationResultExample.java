package com.bigvisible.kanbansimulatortester.common;

import java.util.LinkedList;
import java.util.List;

import com.bigvisible.kanbansimulator.IterationResult;

public class IterationResultExample {
    public int iteration;
    public int putInPlay;
    public int bACapacity;
    public int bACompleted;
    public int bARemainingInQueue;
    public int devCapacity;
    public int devCompleted;
    public int devRemainingInQueue;
    public int webDevCapacity;
    public int webDevCompleted;
    public int webDevRemainingInQueue;
    public int qACapacity;
    public int qACompleted;
    public int qARemainingInQueue;
    public int totalCompleted;
    
    

    /**
     * Converts a collection of {@link IterationResult}s into a corresponding collection
     * of {@link IterationResultExample}s to facilitate comparison.
     * 
     * @param results actual iteration results to convert from
     * @return the same data in example format
     */
	public static List<IterationResultExample> asExample(
			List<IterationResult> results) {
		List<IterationResultExample> examples = new LinkedList<IterationResultExample>();
		
		for (IterationResult iterationResult : results) {
			examples.add(asExample(iterationResult));
		}
		
		return examples;
	}
	

	/**
	 * Converts an {@link IterationResult} into a corresponding {@link IterationResultExample} to
	 * facilitate comparison.
	 * 
	 * @param result the actual iteration result from which to convert
	 * @return the same data in example format
	 */
	public static IterationResultExample asExample(IterationResult result) {
		IterationResultExample example = new IterationResultExample();
		
		example.iteration = result.getIterationNumber();
		example.putInPlay = result.getPutIntoPlay();
		example.bACapacity = result.getCapacity("BA");
		example.bACompleted = result.getCompleted("BA");
		example.bARemainingInQueue = result.getQueued("BA");
		example.devCapacity = result.getCapacity("Dev");
		example.devCompleted = result.getCompleted("Dev");
		example.devRemainingInQueue = result.getQueued("Dev");
		example.webDevCapacity = result.getCapacity("WebDev");
		example.webDevCompleted = result.getCompleted("WebDev");
		example.webDevRemainingInQueue = result.getQueued("WebDev");
		example.qACapacity = result.getCapacity("QA");
		example.qACompleted = result.getCompleted("QA");
		example.qARemainingInQueue = result.getQueued("QA");
		example.totalCompleted = result.getTotalCompleted();
		
		return example;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bACapacity;
		result = prime * result + bACompleted;
		result = prime * result + bARemainingInQueue;
		result = prime * result + devCapacity;
		result = prime * result + devCompleted;
		result = prime * result + devRemainingInQueue;
		result = prime * result + iteration;
		result = prime * result + putInPlay;
		result = prime * result + qACapacity;
		result = prime * result + qACompleted;
		result = prime * result + qARemainingInQueue;
		result = prime * result + totalCompleted;
		result = prime * result + webDevCapacity;
		result = prime * result + webDevCompleted;
		result = prime * result + webDevRemainingInQueue;
		return result;
	}


	/**
	 * Two Iteration Result Examples are equal if all of their fields are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IterationResultExample)) {
			return false;
		}
		IterationResultExample other = (IterationResultExample) obj;
		if (bACapacity != other.bACapacity) {
			return false;
		}
		if (bACompleted != other.bACompleted) {
			return false;
		}
		if (bARemainingInQueue != other.bARemainingInQueue) {
			return false;
		}
		if (devCapacity != other.devCapacity) {
			return false;
		}
		if (devCompleted != other.devCompleted) {
			return false;
		}
		if (devRemainingInQueue != other.devRemainingInQueue) {
			return false;
		}
		if (iteration != other.iteration) {
			return false;
		}
		if (putInPlay != other.putInPlay) {
			return false;
		}
		if (qACapacity != other.qACapacity) {
			return false;
		}
		if (qACompleted != other.qACompleted) {
			return false;
		}
		if (qARemainingInQueue != other.qARemainingInQueue) {
			return false;
		}
		if (totalCompleted != other.totalCompleted) {
			return false;
		}
		if (webDevCapacity != other.webDevCapacity) {
			return false;
		}
		if (webDevCompleted != other.webDevCompleted) {
			return false;
		}
		if (webDevRemainingInQueue != other.webDevRemainingInQueue) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return String
				.format("IterationResultExample [iteration=%s, bARemainingInQueue=%s, devRemainingInQueue=%s, webDevRemainingInQueue=%s, qARemainingInQueue=%s, totalCompleted=%s]",
						iteration, bARemainingInQueue, devRemainingInQueue,
						webDevRemainingInQueue, qARemainingInQueue,
						totalCompleted);
	}
	
	
}
