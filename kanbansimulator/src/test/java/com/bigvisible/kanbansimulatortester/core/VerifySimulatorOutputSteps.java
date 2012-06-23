package com.bigvisible.kanbansimulatortester.core;

import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.bigvisible.kanbansimulator.IterationResult;
import com.bigvisible.kanbansimulatortester.common.IterationResultExample;
import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Then;

public class VerifySimulatorOutputSteps extends StepDefinitionForSimulatorSpecification {

    @Then("^the simulator generates a .csv file$")
    public void the_simulator_generates_a_csv_file() {
        assertTrue(getResultsFile().exists());
    }

    @Then("^the .csv file includes the following results:$")
    public void the_csv_file_includes_the_following_results(List<IterationResultExample> results) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(getResultsFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Trying to open \"" + getResultsFile().getAbsolutePath() + "\" for reading.", e);
        }

        List<IterationResult> resultsFromCSV = IterationResult.parseCSV(inputStream);
        List<IterationResultExample> actualContents = IterationResultExample.asExample(resultsFromCSV);

        for (IterationResultExample iterationResultExample : results) {
            assertThat("(looking for example in the list of actual results)", iterationResultExample,
                    isIn(actualContents));
        }
    }

}
