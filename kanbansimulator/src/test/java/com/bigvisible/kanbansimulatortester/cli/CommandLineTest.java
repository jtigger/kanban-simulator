package com.bigvisible.kanbansimulatortester.cli;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import com.bigvisible.kanbansimulator.CommandLine;

public class CommandLineTest {

	@Test
	public void when_passed_proper_set_of_parameters_then_simulator_runs_successfully() {
		String args[] = new String[] { "88", "13", "12", "12", "10", "11", "0" };

		ByteArrayOutputStream rawOuput = new ByteArrayOutputStream();

		CommandLine.redirectOutputTo(rawOuput);

		CommandLine.main(args);

		String output = rawOuput.toString();

		assertThat(output, containsString("1, 11, 13, 11, 0, 12, 11, 0, 12, 11, 0, 10, 10, 1, 10"));
	}

	@Test
	public void when_not_given_any_parameters_displays_a_help_message()
			throws Exception {
		String args[] = new String[] {};

		ByteArrayOutputStream rawOuput = new ByteArrayOutputStream();

		CommandLine.redirectOutputTo(rawOuput);

		CommandLine.main(args);

		String output = rawOuput.toString();

		assertTrue(output.contains("Usage:"));
	}

}
