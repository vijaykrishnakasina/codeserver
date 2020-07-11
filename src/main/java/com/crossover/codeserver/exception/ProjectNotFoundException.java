package com.crossover.codeserver.exception;

public class ProjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4145889657994379728L;

	public ProjectNotFoundException(Long id) {

        super(String.format("Project with Id %d not found", id));
    }
}
