package com.bigvisible.kanbansimulatortester.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigvisible.kanbansimulator.Simulator;
import com.bigvisible.kanbansimulator.SimulatorEngine;

/**
 * <p>
 * A Step Definition targeted at an instance of a {@link SimulatorEngine}. For a given Scenario, all subclasses will be
 * injected with and share the same instance of {@link SimulatorScenarioContext}, automatically.
 * </p>
 * <p>
 * This structure enables us to organize individual Step Definitions in a scheme of classes of our choosing. A given
 * .feature, can invoke Given, When, and Then Step Definitions from more than one Step Definition class and those
 * individual Step Definitions share context, automatically. Without this structure, we would be forced to put all of
 * our Step Definition methods in one class, which would quickly become unwieldy and hard to comprehend/maintain.
 * </p>
 */
public abstract class StepDefinitionForSimulatorSpecification {

    private SimulatorScenarioContext context;

    public StepDefinitionForSimulatorSpecification() {
        super();
    }

    @Autowired
    public void setContext(SimulatorScenarioContext context) {
        this.context = context;
    }
    
    protected void setSimulatorAsGUI() {
        context.setSimulatorAsGUI();
    }
    
    protected Simulator getStimulator() {
        return context.getStimulator();
    }

    protected OutputStream getResultsOutput() {
        return context.getResultsOutput();
    }

    protected File getResultsFile() {
        return context.getResultsFile();
    }

    protected void setResultsOutput(FileOutputStream fileOutputStream) {
        context.setResultsOutput(fileOutputStream);
    }

    protected void setResultsFile(File file) {
        context.setResultsFile(file);
    }

}