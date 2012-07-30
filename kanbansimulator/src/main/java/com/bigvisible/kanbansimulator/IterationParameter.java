package com.bigvisible.kanbansimulator;

import java.util.HashMap;
import java.util.Map;

public class IterationParameter {

    private int iteration;
    private int batchSize;
    private WorkflowStepParameter workflowStepParameter = new WorkflowStepParameter();
    private Map<String, WorkflowStepParameter> stepNameToParameter = new HashMap<String, WorkflowStepParameter>();

    public String getWorkflowStepName() {
        return workflowStepParameter.getWorkflowStepName();
    }

    public int getIteration() {
        return iteration;
    }

    public Integer getCapacity() {
        return workflowStepParameter.getCapacity();
    }
    
    public IterationParameter setCapacity(int capacity) {
        workflowStepParameter.setCapacity(capacity);
        return this;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public IterationParameter forStep(WorkflowStepParameter workflowStepParameter) {
        this.workflowStepParameter = workflowStepParameter;
        stepNameToParameter.put(workflowStepParameter.getWorkflowStepName(), workflowStepParameter);
        return this;
    }

    public static IterationParameter startingAt(int iteration) {
        IterationParameter newParameter = new IterationParameter();
        newParameter.setIteration(iteration);
        return newParameter;
    }

    public IterationParameter setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public boolean hasWorkflowConfiguration() {
        return workflowStepParameter.getWorkflowStepName() != null;
    }
    
    
    static public class WorkflowStepParameter
    {
        private Integer capacity;
        private String workflowStepName;
        
        static public WorkflowStepParameter named(String workflowStepName) {
            WorkflowStepParameter workflowStepParameter = new WorkflowStepParameter();
            workflowStepParameter.setWorkflowStepName(workflowStepName);
            return workflowStepParameter;
        }
        
        public String getWorkflowStepName() {
            return workflowStepName;
        }
        public void setWorkflowStepName(String workflowStepName) {
            this.workflowStepName = workflowStepName;
        }
        public Integer getCapacity() {
            return capacity;
        }
        public WorkflowStepParameter setCapacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

    }

    public WorkflowStepParameter getParameterForStep(String workflowStepName) {
        return stepNameToParameter.get(workflowStepName);
    }

}
