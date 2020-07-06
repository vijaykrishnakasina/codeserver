package com.crossover.codeserver.services;

import org.springframework.stereotype.Service;

import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.repositories.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;

	public Project getProject(long id) {
		return projectRepository.findById(id).get();
	}
}
