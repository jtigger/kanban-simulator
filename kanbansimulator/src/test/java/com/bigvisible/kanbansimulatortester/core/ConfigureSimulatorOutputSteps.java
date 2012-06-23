package com.bigvisible.kanbansimulatortester.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.bigvisible.kanbansimulatortester.common.StepDefinitionForSimulatorSpecification;

import cucumber.annotation.en.Given;

public class ConfigureSimulatorOutputSteps extends StepDefinitionForSimulatorSpecification {
    @Given("^the desired output is comma-separated values$")
    public void the_desired_output_is_comma_separated_values() {
        setResultsFile(new File("resultsOutput.csv"));

        try {
            setResultsOutput(new FileOutputStream(getResultsFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Trying to open file \""
                            + getResultsFile().getAbsolutePath()
                            + "\" for writing.  Does this process have permissions to write in this directory?",
                    e);
        }
    }
}
