package specs.steps;

import static com.bigvisible.kanbansimulator.IterationParameter.startingAt;

import java.util.List;

import specs.IterationParameterExample;
import cucumber.annotation.en.Given;

public class ConfigurableWorkflowCapacitiesForEachIterationSteps extends StepDefinitionForSimulatorSpecification {

    @Given("^the following workflow capacities by iteration:$")
    public void the_following_workflow_capacities_by_iteration(List<IterationParameterExample> parameterExamples) {
        for (IterationParameterExample parameterExample : parameterExamples) {
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("BA").setCapacity(parameterExample.bACapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("Dev").setCapacity(parameterExample.devCapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("WebDev").setCapacity(
                            parameterExample.webDevCapacity));
            getStimulator().addParameter(
                    startingAt(parameterExample.iteration).forStep("QA").setCapacity(parameterExample.qACapacity));
        }
    }
}
