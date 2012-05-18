package com.bigvisible.kanbansimulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Stimulator {

	public void run(File resultsFile) {
		try {
			FileWriter file = new FileWriter(resultsFile);
			file.write("11,13,11,0");
			file.flush();
			file.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
