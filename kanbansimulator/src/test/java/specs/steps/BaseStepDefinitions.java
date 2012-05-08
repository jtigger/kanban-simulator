package specs.steps;

import static org.junit.Assert.*;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.runtime.PendingException;

public class BaseStepDefinitions {
	@Given("^some context expressed in the past-tense$")
	public void some_context_expressed_in_the_past_tense() {
	    // Express the Regexp above with the code you wish you had
	    throw new PendingException();
	}

	@When("^some action or event occurs in the present tense$")
	public void some_action_or_event_occurs_in_the_present_tense() {
	    // Express the Regexp above with the code you wish you had
	    throw new PendingException();
	}

	@Then("^some verifiable result will be written in the future tense.$")
	public void some_verifiable_result_will_be_written_in_the_future_tense() {
	    // Express the Regexp above with the code you wish you had
	    throw new PendingException();
	}

}
