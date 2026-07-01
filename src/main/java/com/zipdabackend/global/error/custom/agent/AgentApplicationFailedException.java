package com.zipdabackend.global.error.custom.agent;

public class AgentApplicationFailedException extends RuntimeException {
    public AgentApplicationFailedException(String message) {
        super(message);
    }
}
