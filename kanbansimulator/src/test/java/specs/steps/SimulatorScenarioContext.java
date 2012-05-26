package specs.steps;

import java.io.File;
import java.io.OutputStream;

import com.bigvisible.kanbansimulator.Stimulator;


public class SimulatorScenarioContext {
	private Stimulator stimulator;
	private OutputStream resultsOutput = null;
	private File resultsFile = null;

	public SimulatorScenarioContext() {
		stimulator = new Stimulator();
	}
	
	public Stimulator getStimulator() {
		return stimulator;
	}
	
	public OutputStream getResultsOutput() {
		return resultsOutput;
	}

	public void setResultsOutput(OutputStream resultsOutput) {
		this.resultsOutput = resultsOutput;
	}
	
	public File getResultsFile() {
		return resultsFile;
	}

	public void setResultsFile(File resultsFile) {
		this.resultsFile = resultsFile;
	}
}
