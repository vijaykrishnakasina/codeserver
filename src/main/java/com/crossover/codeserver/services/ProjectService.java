package com.crossover.codeserver.services;

import java.util.Map;

import javax.validation.Valid;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;

public interface ProjectService {

    ProjectDto getProject(long id);
    ProjectDto createProject(ProjectDto project);
	ProjectDto patchProject(long projectId, Map<Object, Object> project);
}
