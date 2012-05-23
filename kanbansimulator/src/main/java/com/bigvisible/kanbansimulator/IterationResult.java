package com.bigvisible.kanbansimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
			if (step.getDescription().equalsIgnoreCase(description)) {
				return step;
			}
		}
		return null;
	}

	public void run() {
		int inputFromPreviousStep = putIntoPlay;
		for (WorkflowStep step : steps) {
			step.setQueued(step.getQueued() + inputFromPreviousStep);
			step.setCompleted(Math.min(step.getQueued(), step.getCapacity()));
			step.setQueued(step.getQueued() - step.getCompleted());
			inputFromPreviousStep = step.getCompleted();
		}
		totalCompleted += inputFromPreviousStep;
	}

	public IterationResult nextIteration() {
		IterationResult nextIteration = new IterationResult();
		nextIteration.iterationNumber = iterationNumber + 1;
		for (WorkflowStep step : steps) {
			WorkflowStep sameStepInNextIteration = nextIteration.getStep(step
					.getDescription());

			sameStepInNextIteration.setCapacity(step.getCapacity());
			sameStepInNextIteration.setQueued(step.getQueued());
		}
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

	public int getCapacity(String stepName) {
		return getStep(stepName).getCapacity();
	}

	public void setCapacity(String stepName, int newCapacity) {
		getStep(stepName).setCapacity(newCapacity);
	}

	public int getCompleted(String stepName) {
		return getStep(stepName).getCompleted();
	}

	public int getQueued(String stepName) {
		return getStep(stepName).getQueued();
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

	public void setCompleted(String stepName, int completed) {
		getStep(stepName).setCompleted(completed);
	}

	public void setQueued(String stepName, int queued) {
		getStep(stepName).setQueued(queued);
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

	public String toCSVString() {
		StringBuffer csv = new StringBuffer(steps.size() * 10); // why not avoid
																// reallocation
																// if we can? :)

		csv.append(getIterationNumber() + ", ");
		csv.append(getPutIntoPlay() + ", ");

		for (WorkflowStep step : steps) {
			csv.append(step.getCapacity() + ", ");
			csv.append(step.getCompleted() + ", ");
			csv.append(step.getQueued() + ", ");
		}

		csv.append(getTotalCompleted());

		return csv.toString();
	}

	public static IterationResult parseCSV(String asCSV) {
		IterationResult iterationResult = new IterationResult();
		String[] resultValues = asCSV.split(",");
		int idx = 0;

		iterationResult.iterationNumber = Integer.parseInt(resultValues[idx++]
				.trim());
		iterationResult.putIntoPlay = Integer.parseInt(resultValues[idx++]
				.trim());
		for (WorkflowStep step : iterationResult.steps) {
			step.capacity = Integer.parseInt(resultValues[idx++].trim());
			step.completed = Integer.parseInt(resultValues[idx++].trim());
			step.queued = Integer.parseInt(resultValues[idx++].trim());
		}
		iterationResult.totalCompleted = Integer.parseInt(resultValues[idx++]
				.trim());

		return iterationResult;
	}

	public static List<IterationResult> parseCSV(InputStream inputStream) {
		List<IterationResult> iterationResults = new LinkedList<IterationResult>();

		BufferedReader inputReader = new BufferedReader(new InputStreamReader(
				inputStream));

		int lineNo = 1; // we're assuming we got first dibs on this stream
						// and that it's currently rewound.

		String nextLine;
		do {
			try {
				nextLine = inputReader.readLine();
			} catch (IOException e) {
				throw new RuntimeException("Trying to read line #" + lineNo
						+ ".", e);
			}
			if (nextLine != null) {
				iterationResults.add(parseCSV(nextLine));
			}
		} while (nextLine != null);

		return iterationResults;
	}
}
