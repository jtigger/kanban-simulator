package com.bigvisible.kanbansimulatortester.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bigvisible.kanbansimulatortester.app.WorkflowModelAsAModelForAJTableSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationParameterSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultConfigurationSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultCreatingNextIterationResultSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultSerializationSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultSimulatesRunningAnIterationSpec;
import com.bigvisible.kanbansimulatortester.core.unit.SimulatorConfigurationSpec;
import com.bigvisible.kanbansimulatortester.core.unit.SimulatorOutOfTheBoxSpec;

@RunWith(Suite.class)
@SuiteClasses({
    IterationParameterSpec.class,
    IterationResultConfigurationSpec.class,
    IterationResultCreatingNextIterationResultSpec.class,
    IterationResultSimulatesRunningAnIterationSpec.class,
    IterationResultSerializationSpec.class,
    SimulatorConfigurationSpec.class,
    SimulatorOutOfTheBoxSpec.class,
    WorkflowModelAsAModelForAJTableSpec.class
        })
public class AllUnitTests {
}
