package specs.steps;

import java.io.File;
import java.io.OutputStream;

import com.bigvisible.kanbansimulator.GUI;
import com.bigvisible.kanbansimulator.GUIDriver;
import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;


public class SimulatorScenarioContext {
	private Simulator stimulator;
	private OutputStream resultsOutput = null;
	private File resultsFile = null;

	public SimulatorScenarioContext() {
		stimulator = new SimulatorEngine();
	}
	
	public Simulator getStimulator() {
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

    public void setSimulatorAsGUI() {
        stimulator = new GUIDriver();
    }
}
