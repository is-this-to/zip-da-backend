package com.zipdabackend.global.error.custom.agent;

public class AgentApplicationAlreadyExistsException extends RuntimeException {
    public AgentApplicationAlreadyExistsException(String message) {
        super(message);
    }
}
