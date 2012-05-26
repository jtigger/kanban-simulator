package specs.steps;

import java.io.File;
import java.io.OutputStream;

import com.bigvisible.kanbansimulator.Stimulator;

public class SimulatorFeatureContext {
	private static SimulatorFeatureContext instance;
	
	private Stimulator stimulator;
	private OutputStream resultsOutput = null;
	private File resultsFile = null;

	protected Stimulator getStimulator() {
		if (stimulator == null) {
			stimulator = new Stimulator();
		}
		return stimulator;
	}
	
	protected OutputStream getResultsOutput() {
		return resultsOutput;
	}

	protected void setResultsOutput(OutputStream resultsOutput) {
		this.resultsOutput = resultsOutput;
	}
	
	protected File getResultsFile() {
		return resultsFile;
	}

	protected void setResultsFile(File resultsFile) {
		this.resultsFile = resultsFile;
	}

	public static SimulatorFeatureContext instance() {
		if(instance == null) {
			instance = new SimulatorFeatureContext();
		}
		return instance;
	}

	public static void reset() {
		instance = null;
	}

}
