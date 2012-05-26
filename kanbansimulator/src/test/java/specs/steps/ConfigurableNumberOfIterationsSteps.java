package specs.steps;

import org.springframework.beans.factory.annotation.Autowired;

import com.bigvisible.kanbansimulator.Stimulator;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.runtime.PendingException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ConfigurableNumberOfIterationsSteps {
	
	private SimulatorScenarioContext context;

	@Autowired
	public void setContext(SimulatorScenarioContext context) {
		this.context = context;
	}

	private Stimulator getStimulator() {
		return context.getStimulator();
	}
	
	@Given("^I set the number of iterations to (\\d+)$")
	public void I_set_the_number_of_iterations_to(int numOfIterations) {
		getStimulator().setNumberOfIterationsToRun(numOfIterations);
	}

	@Then("^the simulator will have generated exactly (\\d+) iterations$")
	public void the_simulator_will_have_generated_iterations(int numOfIterations) {
		assertThat(getStimulator().getIterationsRun(), is(numOfIterations));
	}
}
