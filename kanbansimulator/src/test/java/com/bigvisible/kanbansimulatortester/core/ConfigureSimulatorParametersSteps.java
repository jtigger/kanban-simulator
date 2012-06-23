package com.bigvisible.kanbansimulatortester.core;

import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.runtime.PendingException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class ConfigureSimulatorParametersSteps extends StepDefinitionForSimulatorSpecification {

    @Given("^I set the number of iterations to (\\d+)$")
    public void I_set_the_number_of_iterations_to(int numOfIterations) {
        getStimulator().setNumberOfIterationsToRun(numOfIterations);
    }

    @Given("^the BA capacity is (\\d+) stories per iteration$")
    public void the_BA_capacity_is_stories_per_iteration(int businessAnalystCapacity) {
        getStimulator().setBusinessAnalystCapacity(businessAnalystCapacity);
    }

    @Given("^the Dev capacity is (\\d+) stories per iteration$")
    public void the_Dev_capacity_is_stories_per_iteration(int developmentCapacity) {
        getStimulator().setDevelopmentCapacity(developmentCapacity);
    }

    @Given("^the Web Dev capacity is (\\d+) stories per iteration$")
    public void the_Web_Dev_capacity_is_stories_per_iteration(int webDevelopmentCapacity) {
        getStimulator().setWebDevelopmentCapacity(webDevelopmentCapacity);
    }

    @Given("^the QA capacity is (\\d+) stories per iteration$")
    public void the_QA_capacity_is_stories_per_iteration(int qualityAssuranceCapacity) {
        getStimulator().setQualityAssuranceCapacity(qualityAssuranceCapacity);
    }

    @Given("^the backlog starts with (\\d+) stories$")
    public void the_backlog_starts_with_stories(int numberOfStories) {
        getStimulator().addStories(numberOfStories);
    }

    @Given("^the batch size is (\\d+) stories$")
    public void the_batch_size_is_stories(int batchSize) {
        getStimulator().setBatchSize(batchSize);
    }

    @Then("^the simulator will have generated exactly (\\d+) iterations$")
    public void the_simulator_will_have_generated_iterations(int numOfIterations) {
        assertThat(getStimulator().getIterationsRun(), is(numOfIterations));
    }

}
