package specs.steps;

import cucumber.annotation.en.Given;

public class GUISteps extends StepDefinitionForSimulatorSpecification {
    
    @Given("^we have started the Kanban Simulator Application$")
    public void we_have_started_the_Kanban_Simulator_Application() {
        setSimulatorAsGUI();
    }
}
