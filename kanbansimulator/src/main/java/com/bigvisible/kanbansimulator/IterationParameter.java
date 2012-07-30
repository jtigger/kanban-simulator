package com.bigvisible.kanbansimulator;

public class IterationParameter {

    private int iteration;
    private int batchSize;
    private WorkflowStepParameter workflowStepParameter = new WorkflowStepParameter();
    

    public String getWorkflowStepName() {
        return workflowStepParameter.getWorkflowStepName();
    }

    public int getIteration() {
        return iteration;
    }

    public Integer getCapacity() {
        return workflowStepParameter.getCapacity();
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public IterationParameter forStep(String workflowStepName) {
        workflowStepParameter.setWorkflowStepName(workflowStepName);
        return this;
    }

    public IterationParameter setCapacity(Integer capacity) {
        workflowStepParameter.setCapacity(capacity);
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

    public boolean isWorkflowConfiguration() {
        return workflowStepParameter.getWorkflowStepName() != null;
    }
    
    private class WorkflowStepParameter
    {
        private Integer capacity;
        private String workflowStepName;
        public String getWorkflowStepName() {
            return workflowStepName;
        }
        public void setWorkflowStepName(String workflowStepName) {
            this.workflowStepName = workflowStepName;
        }
        public Integer getCapacity() {
            return capacity;
        }
        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

    }

}
