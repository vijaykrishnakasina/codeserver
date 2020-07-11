package com.crossover.codeserver.services;

import java.util.Map;

import com.crossover.codeserver.dto.ProjectDto;

public interface ProjectService {

    ProjectDto getProject(Long id);
    ProjectDto createProject(ProjectDto project);
	ProjectDto patchProject(Long projectId, Map<Object, Object> project);
}
