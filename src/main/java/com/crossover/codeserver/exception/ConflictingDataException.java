package com.crossover.codeserver.exception;

public class ConflictingDataException extends RuntimeException {

	private static final long serialVersionUID = 1638032401695552924L;

	public ConflictingDataException() {

        super(String.format("Conflicting input data"));
    }
}
