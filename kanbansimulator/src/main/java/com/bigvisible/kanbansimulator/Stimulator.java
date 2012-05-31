package com.bigvisible.kanbansimulator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Stimulator {
	private int totalStories = 0;
	private int storiesCompleted = 0;
	private int storiesUnplayed = 0;
	private int batchSize = 1;
	private int businessAnalystCapacity = 1;
	private int developmentCapacity = 1;
	private int webDevelopmentCapacity = 1;
	private int qualityAssuranceCapacity = 1;
	private List<IterationResult> results = new LinkedList<IterationResult>();
	private int numberOfIterationsToRun;

	public void run(OutputStream rawOutputStream) {
		if(rawOutputStream == null) {
			rawOutputStream = new NullOutputStream();
		}
	    
		PrintWriter output = new PrintWriter(rawOutputStream);
		
		storiesUnplayed = totalStories;
	    
		IterationResult iteration = null;
        while (haveMoreIterationsToRun()) {
            iteration = runNextIteration(iteration, Math.min(storiesUnplayed, batchSize));
            updateSimulatorState(iteration);
            outputIterationResults(output, iteration);
        }
		// It's this PrintWriter instance that's buffering, calling flush() on the wrapped
		// raw OutputStream will have no effect.
		output.flush();
	}

    private boolean haveMoreIterationsToRun() {
        boolean thereAreASetNumberOfIterationsToRun = numberOfIterationsToRun > 0;
        
        if(thereAreASetNumberOfIterationsToRun) {
            return getIterationsRun() < numberOfIterationsToRun;
        } else {
            return thereAreStoriesLeftToPlay();
        }
    }

    private boolean thereAreStoriesLeftToPlay() {
        return storiesCompleted < totalStories;
    }

	private IterationResult runNextIteration(IterationResult previousIteration, int storiesToPlay) {
		IterationResult iteration;
		
		if(previousIteration == null) {
			iteration = new IterationResult();
			iteration.setIterationNumber(1);
			iteration.setCapacity("BA", businessAnalystCapacity);
			iteration.setCapacity("Dev", developmentCapacity);
			iteration.setCapacity("WebDev", webDevelopmentCapacity);
			iteration.setCapacity("QA", qualityAssuranceCapacity);

		} else {
			iteration = previousIteration.nextIteration();
		}
		iteration.setPutIntoPlay(storiesToPlay);
		
		iteration.run();
		
		return iteration;
	}

	private void updateSimulatorState(IterationResult iteration) {
		storiesUnplayed -= iteration.getPutIntoPlay();
		storiesCompleted = iteration.getTotalCompleted();
		
	    results.add(iteration);
	}

	private void outputIterationResults(PrintWriter output,
			IterationResult iteration) {
		output.println(iteration.toCSVString());
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

	public void setNumberOfIterationsToRun(int numberOfIterationsToRun) {
		this.numberOfIterationsToRun = numberOfIterationsToRun;
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
	
	/**
	 * OutputStream that simply sinks all data given to it.
	 */
	public static class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
			// this is where bytes go to die.
		}

	}

	public int getIterationsRun() {
		return results.size();
	}

}
