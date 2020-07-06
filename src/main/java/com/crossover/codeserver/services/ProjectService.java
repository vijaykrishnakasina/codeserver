package com.crossover.codeserver.services;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;

public interface ProjectService {

    Project getProject(long id);
    Project createProject(Long SystemId,ProjectDto projectDto);
    Project updateProject(Long projectId,ProjectDto projectDetails);
}
