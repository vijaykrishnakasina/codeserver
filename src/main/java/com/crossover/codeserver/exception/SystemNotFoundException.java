package com.crossover.codeserver.exception;

public class SystemNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3509224468603919981L;

	public SystemNotFoundException(Long id) {

        super(String.format("System with Id %d not found", id));
    }
}
