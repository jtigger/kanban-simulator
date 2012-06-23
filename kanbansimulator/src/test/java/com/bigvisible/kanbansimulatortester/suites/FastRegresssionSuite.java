package com.bigvisible.kanbansimulatortester.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllUnitTests.class, AllFunctionalSpecs.class })
public class FastRegresssionSuite {
}
