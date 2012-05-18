package specs.steps;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;

import com.bigvisible.kanbansimulator.Stimulator;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class BaseStepDefinitions {
	private Stimulator stimulator;
	private File resultsFile;

	@Given("^we hard-code the Agile PMO simulation configuration$")
	public void we_hard_code_the_Agile_PMO_simulation_configuration() {
		stimulator = new Stimulator();
	}

	@When("^the simulator completes a run$")
	public void the_simulator_completes_a_run() {
		resultsFile = new File("output.csv");
		stimulator.run(resultsFile);
	}

	@Then("^it generates a .csv file containing the iteration-by-iteration results \\(exactly as seen in the Agile PMO tab of the spreadsheet\\); with column titles.$")
	public void it_generates_a_csv_file_containing_the_iteration_by_iteration_results_exactly_as_seen_in_the_Agile_PMO_tab_of_the_spreadsheet_with_column_titles() {
		String expectedFirstLine = "1,11,13,11,0";
		
		String contents;
		contents = readCSVFileContents();
		
		assertThat(contents, startsWith(expectedFirstLine));
	}

	private String readCSVFileContents() {
		try {
			FileReader file = new FileReader(resultsFile);
			CharBuffer buffer = CharBuffer.allocate(1024);
			int charsRead = file.read(buffer);

			buffer.rewind();
			
			return buffer.subSequence(0, charsRead).toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
