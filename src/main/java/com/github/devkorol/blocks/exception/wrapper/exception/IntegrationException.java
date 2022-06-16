package com.github.devkorol.blocks.exception.wrapper.exception;

import lombok.Getter;

@Getter
public class IntegrationException extends RuntimeException {

    /**
     * Service name which thrown exception.
     */
    private final String serviceName;

    public IntegrationException(String serviceName, String message, Throwable e) {
        super(message, e);
        this.serviceName = serviceName;
    }
}
