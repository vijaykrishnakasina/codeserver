package com.crossover.codeserver.services;

import java.util.Map;

import com.crossover.codeserver.dto.ProjectDto;

public interface ProjectService {

    ProjectDto getProject(long id);
    ProjectDto createProject(ProjectDto project);
	ProjectDto patchProject(long projectId, Map<Object, Object> project);
}
