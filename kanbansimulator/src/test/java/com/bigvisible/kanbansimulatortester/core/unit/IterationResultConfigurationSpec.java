package com.bigvisible.kanbansimulatortester.core.unit;

import static com.bigvisible.kanbansimulator.IterationParameter.startingAt;
import static com.bigvisible.kanbansimulator.IterationParameter.WorkflowStepParameter.named;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.bigvisible.kanbansimulator.IterationParameter;
import com.bigvisible.kanbansimulator.IterationResult;

public class IterationResultConfigurationSpec {

    @Test
    public void GIVEN_configuration_sets_batch_size_WHEN_the_iteration_is_configured_THEN_the_batch_size_of_the_iteration_is_that_value() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        
        int configuredBatchSize = UnitTestHelper.anyReasonableNumber();
        
        params.add(startingAt(1).setBatchSize(configuredBatchSize));
        iteration.configure(params);
        
        assertThat(iteration.getBatchSize(), is(configuredBatchSize));
    }

    @Test
    public void GIVEN_configuration_sets_capacity_for_a_workflow_step_WHEN_the_iteration_is_configured_THEN_the_capacity_of_that_workflow_step_is_that_value() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        
        String someWorkflowStep = "Dev";
        int configuredCapacity = UnitTestHelper.anyReasonableNumber();
        
        params.add(startingAt(1).forStep(named(someWorkflowStep)).setCapacity(configuredCapacity));
        iteration.configure(params);
        
        assertThat(iteration.getCapacity(someWorkflowStep), is(configuredCapacity));
        
    }

    @Test
    public void GIVEN_two_configuration_set_the_same_parameter_WHEN_the_iteration_is_configured_THEN_the_last_configuration_wins() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        int firstConfiguredBatchSize = 10;
        int secondConfiguredBatchSize = 20;

        params.add(startingAt(1).setBatchSize(firstConfiguredBatchSize));
        params.add(startingAt(1).setBatchSize(secondConfiguredBatchSize));
        
        iteration.configure(params);
        
        assertThat(iteration.getBatchSize(), is(secondConfiguredBatchSize));
    }
    
    @Test
    public void GIVEN_configuration_says_to_remove_a_workflow_step_WHEN_the_iteration_is_configured_THEN_the_iteration_does_not_have_that_workflow_step() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        params.add(startingAt(1).forStep(named("WebDev").remove()));
        
        iteration.configure(params);
        
        assertThat("WebDev", not(isIn(iteration.workflowStepNames())));
    }
}
