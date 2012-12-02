package com.bigvisible.kanbansimulatortester.common;

import java.io.File;
import java.io.OutputStream;

import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;
import com.bigvisible.kanbansimulatortester.app.GUIDriver;


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
        GUIDriver guiDriver = new GUIDriver();
        stimulator = guiDriver;
        
        guiDriver.start();
    }
    
    public void destroy() {
        if(stimulator instanceof GUIDriver) {
            ((GUIDriver)stimulator).finish();
        }
    }
}
