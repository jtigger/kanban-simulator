package com.bigvisible.kanbansimulator;

import static com.bigvisible.kanbansimulator.IterationParameter.WorkflowStepParameter.*;
import static com.bigvisible.kanbansimulator.IterationParameter.*;

import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulatorEngine implements Simulator {
	private int totalStories;
	private int storiesCompleted;
	private int storiesUnplayed;
	private List<IterationResult> results;
	private Map<Integer,List<IterationParameter>> allIterationParameters;
	private int numberOfIterationsToRun;

	public SimulatorEngine() {
	    totalStories = 0;
	    storiesCompleted = 0;
	    storiesUnplayed = 0;
	    results = new LinkedList<IterationResult>();
	    allIterationParameters = new HashMap<Integer,List<IterationParameter>>();

	    setBatchSize(1);
	    setBusinessAnalystCapacity(1);
	    setDevelopmentCapacity(1);
	    setWebDevelopmentCapacity(1);
	    setQualityAssuranceCapacity(1);
    }
	
	public void run(OutputStream rawOutputStream) {
		if(rawOutputStream == null) {
			rawOutputStream = new NullOutputStream();
		}
	    
		PrintWriter output = new PrintWriter(rawOutputStream);
		
		storiesUnplayed = totalStories;
	    
		IterationResult iteration = null;
        while (haveMoreIterationsToRun()) { 
            iteration = runNextIteration(iteration, storiesUnplayed);
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

	private IterationResult runNextIteration(IterationResult previousIteration, int storiesAvailableToPlay) {
		IterationResult iteration;
		
		if(previousIteration == null) {
			iteration = new IterationResult();
			iteration.setIterationNumber(1);
		} else {
			iteration = previousIteration.nextIteration();
		}
		iteration.configure(allIterationParameters.get(iteration.getIterationNumber()));
		iteration.run(storiesAvailableToPlay);
		
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
	    addParameter(startingAt(1).setBatchSize(batchSize));
	}

	public void setBusinessAnalystCapacity(int businessAnalystCapacity) {
	    addParameter(startingAt(1).forStep(named("BA").setCapacity(businessAnalystCapacity)));
	}

	public void setDevelopmentCapacity(int developmentCapacity) {
        addParameter(startingAt(1).forStep(named("Dev").setCapacity(developmentCapacity)));
	}

	public void setWebDevelopmentCapacity(int webDevelopmentCapacity) {
        addParameter(startingAt(1).forStep(named("WebDev").setCapacity(webDevelopmentCapacity)));
	}

	public void setQualityAssuranceCapacity(int qualityAssuranceCapacity) {
        addParameter(startingAt(1).forStep(named("QA").setCapacity(qualityAssuranceCapacity)));
	}

	public void setNumberOfIterationsToRun(int numberOfIterationsToRun) {
		this.numberOfIterationsToRun = numberOfIterationsToRun;
	}

	public void addParameter(IterationParameter iterationParameter) {
        List<IterationParameter> iterationParameters = allIterationParameters.get(iterationParameter.getIteration());
        if(iterationParameters == null) {
            iterationParameters = new LinkedList<IterationParameter>();
            allIterationParameters.put(iterationParameter.getIteration(), iterationParameters);
        }
        iterationParameters.add(iterationParameter);
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
