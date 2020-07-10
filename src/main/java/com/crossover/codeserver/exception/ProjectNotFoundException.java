package com.crossover.codeserver.exception;

public class ProjectNotFoundException extends RuntimeException {

	public ProjectNotFoundException(Long id) {

        super(String.format("Project with Id %d not found", id));
    }
}
