package com.crossover.codeserver.exception;

public class SystemNotFoundException extends RuntimeException {

	public SystemNotFoundException(Long id) {

        super(String.format("System with Id %d not found", id));
    }
}
