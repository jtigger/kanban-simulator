package specs.steps;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;

import com.bigvisible.kanbansimulator.Stimulator;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.runtime.PendingException;

public class BaseStepDefinitions {
	private Stimulator stimulator;
	private File resultsFile;
	
	@Given("^the workflow has (\\d+) steps$")
	public void the_workflow_has_steps(int arg1) {
		// TODO: consider deleting this and just inferring from the capacity setup steps.
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

	@Given("^the batch size is (\\d+) stories$")
	public void the_batch_size_is_stories(int batchSize) {
		getStimulator().setBatchSize(batchSize);
	}

	@When("^the simulator completes a run$")
	public void the_simulator_completes_a_run() {
		resultsFile = new File("output.csv");
		getStimulator().run(resultsFile);
	}

	@Then("^it generates a .csv file containing the iteration-by-iteration results \\(exactly as seen in the Agile PMO tab of the spreadsheet\\); with column titles$")
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

	private Stimulator getStimulator() {
		if(stimulator == null) {
			stimulator = new Stimulator();
		}
		return stimulator;
	}

}
