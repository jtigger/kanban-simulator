package com.bigvisible.kanbansimulatortester.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bigvisible.kanbansimulator.IterationParameter;
import com.bigvisible.kanbansimulatortester.core.unit.IterationParameterTest;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultSerializationSpec;
import com.bigvisible.kanbansimulatortester.core.unit.OutOfTheBoxSimulatorSpec;
import com.bigvisible.kanbansimulatortester.core.unit.ConfiguringTheSimulatorSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultCreatingNextIterationResultSpec;
import com.bigvisible.kanbansimulatortester.core.unit.IterationResultSimulatesRunningAnIterationSpec;

@RunWith(Suite.class)
@SuiteClasses({ IterationResultSimulatesRunningAnIterationSpec.class,
        IterationResultCreatingNextIterationResultSpec.class,
        IterationResultSerializationSpec.class,
        ConfiguringTheSimulatorSpec.class, IterationParameterTest.class, OutOfTheBoxSimulatorSpec.class })
public class AllUnitTests {
}
