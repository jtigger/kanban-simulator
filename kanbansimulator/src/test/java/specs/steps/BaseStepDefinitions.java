package specs.steps;


import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.util.List;

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
	
	@Then("^it generates a .csv file containing the iteration-by-iteration results with the following values:$")
	public void it_generates_a_csv_file_containing_the_iteration_by_iteration_results_with_the_following_values(List<IterationResultExample> results) {
		System.out.println(results.iterator().next().bACapacity);

		// TODO: hmmmm, should we be comparing the results of the CSV file?  or should we
		// be testing the business logic by comparing IterationResultExample instances with
		// IterationResults ?
		
		String contents;
		contents = readCSVFileContents();

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
		if(stimulator == null) {
			stimulator = new Stimulator();
		}
		return stimulator;
	}

}
