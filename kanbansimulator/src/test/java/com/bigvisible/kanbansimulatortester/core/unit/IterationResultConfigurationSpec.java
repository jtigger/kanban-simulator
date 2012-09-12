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

    // TODO-NEXT: enumerate other aspects of the existing contract of "IterationResult.configure()".
    
    @Test
    public void GIVEN_configuration_says_to_remove_a_workflow_step_WHEN_the_iteration_is_configured_THEN_the_iteration_does_not_have_that_workflow_step() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        params.add(startingAt(1).forStep(named("WebDev").remove()));
        
        iteration.configure(params);
        
        assertThat("WebDev", not(isIn(iteration.workflowStepNames())));
    }
    
    @Test
    public void GIVEN_configuration_sets_batch_size_WHEN_the_iteration_is_configured_THEN_the_batch_size_of_the_iteration_is_that_value() throws Exception {
        IterationResult iteration = new IterationResult();
        List<IterationParameter> params = new LinkedList<IterationParameter>();
        
        int configuredBatchSize = UnitTestHelper.anyReasonableNumber();
        
        params.add(startingAt(1).setBatchSize(configuredBatchSize));
        iteration.configure(params);
        
        assertThat(iteration.getBatchSize(), is(configuredBatchSize));
    }
}
