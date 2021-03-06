package com.bigvisible.kanbansimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class IterationResult {
    private int iterationNumber;
    private int batchSize;
    private int totalCompleted;
    private int putIntoPlay;

    private List<WorkflowStep> steps;

    public IterationResult() {
        this("BA", "Dev", "WebDev", "QA");
    }
    
    public IterationResult(List<String> workflowStepNames) {
        this(workflowStepNames.toArray(new String[workflowStepNames.size()]));
    }
    
    public IterationResult(String... workflowStepNames ) {
        steps = new LinkedList<WorkflowStep>();
        for (String workflowStepName : workflowStepNames) {
            steps.add(new WorkflowStep(workflowStepName));
        }
    }
    
    public void configure(List<IterationParameter> iterationParameters) {
        if (iterationParameters == null) {
            return;
        }
        
        for (IterationParameter iterationParameter : iterationParameters) {
            if (iterationParameter.hasWorkflowConfiguration()) {
                WorkflowStep step = getStep(iterationParameter.getWorkflowStepName());
                if (step == null) {
                    throw new InvalidSimulatorConfiguration(String.format(
                            "Attempted to configure workflow step \"%s\" when the defined steps are: [%s]",
                            iterationParameter.getWorkflowStepName(), getStepDescriptions()));
                }
                if (iterationParameter.isToRemove()) {
                    steps.remove(step);
                } else {
                    if (iterationParameter.getCapacity() != null) {
                        step.setCapacity(iterationParameter.getCapacity());
                    }
                }
            } else {
                if(iterationParameter.getBatchSize() != null) {
                  setBatchSize(iterationParameter.getBatchSize());
                }
            }
        }
    }

    public void run(int storiesAvailableToPlay) {
        putIntoPlay = Math.min(storiesAvailableToPlay, batchSize);
        int outputOfPreviousStep = putIntoPlay;
        for (WorkflowStep step : steps) {
            step.setQueued(step.getQueued() + outputOfPreviousStep);
            step.setCompleted(Math.min(step.getQueued(), step.getCapacity()));
            step.setQueued(step.getQueued() - step.getCompleted());
            outputOfPreviousStep = step.getCompleted();
        }
        totalCompleted += outputOfPreviousStep;  // output from last step is considered "completed"
    }

    public IterationResult nextIteration() {
        IterationResult nextIteration = new IterationResult(workflowStepNames());
        nextIteration.iterationNumber = iterationNumber + 1;
        nextIteration.batchSize = batchSize;
        for (WorkflowStep step : steps) {
            WorkflowStep sameStepInNextIteration = 
                    nextIteration.getStep(step.getName());

            sameStepInNextIteration.setCapacity(step.getCapacity());
            sameStepInNextIteration.setQueued(step.getQueued());
        }
        nextIteration.totalCompleted = totalCompleted;

        return nextIteration;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getPutIntoPlay() {
        return putIntoPlay;
    }

    public void setPutIntoPlay(int putIntoPlay) {
        this.putIntoPlay = putIntoPlay;
    }

    public int getCapacity(String stepName) {
        return getStep(stepName).getCapacity();
    }

    public void setCapacity(String stepName, int newCapacity) {
        getStep(stepName).setCapacity(newCapacity);
    }

    public int getCompleted(String stepName) {
        return getStep(stepName).getCompleted();
    }

    public int getQueued(String stepName) {
        return getStep(stepName).getQueued();
    }

    public void setCompleted(String stepName, int completed) {
        getStep(stepName).setCompleted(completed);
    }

    public void setQueued(String stepName, int queued) {
        getStep(stepName).setQueued(queued);
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public String toCSVString() {
        StringBuffer csv = new StringBuffer(steps.size() * 10); // why not avoid
                                                                // reallocation
                                                                // if we can? :)

        csv.append(getIterationNumber() + ", ");
        csv.append(getPutIntoPlay() + ", ");

        for (WorkflowStep step : steps) {
            csv.append(step.getCapacity() + ", ");
            csv.append(step.getCompleted() + ", ");
            csv.append(step.getQueued() + ", ");
        }

        csv.append(getTotalCompleted());

        return csv.toString();
    }

    public static IterationResult parseCSV(String asCSV) {
        IterationResult iterationResult = new IterationResult();
        String[] resultValues = asCSV.split(",");
        int idx = 0;

        iterationResult.iterationNumber = Integer.parseInt(resultValues[idx++].trim());
        iterationResult.putIntoPlay = Integer.parseInt(resultValues[idx++].trim());
        for (WorkflowStep step : iterationResult.steps) {
            step.capacity = Integer.parseInt(resultValues[idx++].trim());
            step.completed = Integer.parseInt(resultValues[idx++].trim());
            step.queued = Integer.parseInt(resultValues[idx++].trim());
        }
        iterationResult.totalCompleted = Integer.parseInt(resultValues[idx++].trim());

        return iterationResult;
    }

    public static List<IterationResult> parseCSV(InputStream inputStream) {
        List<IterationResult> iterationResults = new LinkedList<IterationResult>();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));

        int lineNo = 1; // we're assuming we got first dibs on this stream
                        // and that it's currently rewound.

        String nextLine;
        do {
            try {
                nextLine = inputReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Trying to read line #" + lineNo + ".", e);
            }
            if (nextLine != null) {
                iterationResults.add(parseCSV(nextLine));
            }
        } while (nextLine != null);

        return iterationResults;
    }

    private static class WorkflowStep {
        
        private String name;
        private int capacity;
        private int completed;
        private int queued;
    
        public WorkflowStep(String description) {
            setName(description);
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public int getCapacity() {
            return capacity;
        }
    
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    
        public int getCompleted() {
            return completed;
        }
    
        public void setCompleted(int completed) {
            this.completed = completed;
        }
    
        public int getQueued() {
            return queued;
        }
    
        public void setQueued(int queued) {
            this.queued = queued;
        }
    }

    private WorkflowStep getStep(String description) {
        for (WorkflowStep step : steps) {
            if (step.getName().equalsIgnoreCase(description)) {
                return step;
            }
        }
        return null;
    }

    private String getStepDescriptions() {
        StringBuffer definedStepNames = new StringBuffer();
        String delim = "";
        for (WorkflowStep definedStep : steps) {
            definedStepNames.append(delim).append(definedStep.getName());
            delim = ", ";
        }
        return definedStepNames.toString();
    }

    public List<String> workflowStepNames() {
        List<String> workflowStepNames = new LinkedList<String>();
        
        for (WorkflowStep step : steps) {
            workflowStepNames.add(step.getName());
        }
       
        return workflowStepNames;
    }
}
