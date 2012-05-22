package specs.steps;

import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.CharBuffer;
import java.util.List;

import specs.IterationResultExample;

import com.bigvisible.kanbansimulator.Stimulator;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import cucumber.runtime.PendingException;

public class SimpleSimulationSteps {
	private Stimulator stimulator;
	private File resultsFile = null;
	private OutputStream resultsOutput = null;

	@Given("^the workflow has (\\d+) steps$")
	public void the_workflow_has_steps(int arg1) {
		// TODO: consider deleting this and just inferring from the capacity
		// setup steps.
	}

	@Given("^the BA capacity is (\\d+) stories per iteration$")
	public void the_BA_capacity_is_stories_per_iteration(
			int businessAnalystCapacity) {
		getStimulator().setBusinessAnalystCapacity(businessAnalystCapacity);
	}

	@Given("^the Dev capacity is (\\d+) stories per iteration$")
	public void the_Dev_capacity_is_stories_per_iteration(
			int developmentCapacity) {
		getStimulator().setDevelopmentCapacity(developmentCapacity);
	}

	@Given("^the Web Dev capacity is (\\d+) stories per iteration$")
	public void the_Web_Dev_capacity_is_stories_per_iteration(
			int webDevelopmentCapacity) {
		getStimulator().setWebDevelopmentCapacity(webDevelopmentCapacity);
	}

	@Given("^the QA capacity is (\\d+) stories per iteration$")
	public void the_QA_capacity_is_stories_per_iteration(
			int qualityAssuranceCapacity) {
		getStimulator().setQualityAssuranceCapacity(qualityAssuranceCapacity);
	}

	@Given("^the backlog starts with (\\d+) stories$")
	public void the_backlog_starts_with_stories(int numberOfStories) {
		getStimulator().addStories(numberOfStories);
	}

	@Given("^the batch size is (\\d+) stories$")
	public void the_batch_size_is_stories(int batchSize) {
		getStimulator().setBatchSize(batchSize);
	}

	@Given("^the desired output is comma-separated values$")
	public void the_desired_output_is_comma_separated_values() {
		resultsFile = new File("resultsOutput.csv");

		try {
			resultsOutput = new FileOutputStream(resultsFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(
					"Trying to open file \""
							+ resultsFile.getAbsolutePath()
							+ "\" for writing.  Does this process have permissions to write in this directory?",
					e);
		}
	}

	@When("^the simulator completes a run$")
	public void the_simulator_completes_a_run() {
		getStimulator().run(resultsOutput);
	}

	@Then("^the simulator will have generated the following results:$")
	public void the_simulator_will_have_generated_the_following_results(
			List<IterationResultExample> results) {
		List<IterationResultExample> actualResults = IterationResultExample
				.asExample(getStimulator().results());

		for (IterationResultExample iterationResultExample : results) {
			assertThat("some test", iterationResultExample, isIn(actualResults));
		}
	}

	@Then("^the simulator generates a .csv file$")
	public void the_simulator_generates_a_csv_file() {
		assertTrue(resultsFile.exists());
	}

	@Then("^the .csv file includes the following results:$")
	public void the_csv_file_includes_the_following_results(
			List<IterationResultExample> results) {
		// Express the Regexp above with the code you wish you had
		// For automatic conversion, change DataTable to List<YourType>
		throw new PendingException();
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
		if (stimulator == null) {
			stimulator = new Stimulator();
		}
		return stimulator;
	}
}
