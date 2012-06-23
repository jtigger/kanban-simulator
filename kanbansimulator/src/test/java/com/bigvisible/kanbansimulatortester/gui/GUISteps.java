package com.bigvisible.kanbansimulatortester.gui;

import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;

public class GUISteps extends StepDefinitionForSimulatorSpecification {
    
    @Given("^we have started the Kanban Simulator Application$")
    public void we_have_started_the_Kanban_Simulator_Application() {
        setSimulatorAsGUI();
    }
}
