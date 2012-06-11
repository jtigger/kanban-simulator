package com.bigvisible.kanbansimulator;

import java.io.OutputStream;
import java.util.List;

public interface Simulator {

    public abstract void run(OutputStream rawOutputStream);

    public abstract void setBatchSize(int batchSize);

    public abstract void setBusinessAnalystCapacity(int businessAnalystCapacity);

    public abstract void setDevelopmentCapacity(int developmentCapacity);

    public abstract void setWebDevelopmentCapacity(int webDevelopmentCapacity);

    public abstract void setQualityAssuranceCapacity(int qualityAssuranceCapacity);

    public abstract void setNumberOfIterationsToRun(int numberOfIterationsToRun);

    public abstract void addParameter(IterationParameter iterationParameter);

    public abstract List<IterationResult> results();

    public abstract void addStories(int storiesToAdd);

    public abstract int getStoriesCompleted();

    public abstract int getIterationsRun();

}