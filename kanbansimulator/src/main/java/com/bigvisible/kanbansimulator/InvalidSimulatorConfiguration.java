package com.bigvisible.kanbansimulator;

public class InvalidSimulatorConfiguration extends RuntimeException {
    private static final long serialVersionUID = 9206001096077731596L;

    public InvalidSimulatorConfiguration(String message) {
        super(message);
    }
}
