package com.crossover.codeserver.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.entities.SdlcSystem;
import com.crossover.codeserver.repositories.ProjectRepository;
import com.crossover.codeserver.repositories.SdlcSystemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository projectRepository;
	
	@Autowired
	private SdlcSystemRepository sdlcSystemRepository;

	public Project getProject(long id) {
		return projectRepository.findById(id).get();
	}
	
	public Project createProject( Long systemId,ProjectDto projectDto) {
	
		Project project = Project.builder().externalId(projectDto.getExternalId()).name(projectDto.getName())
				.createdDate(projectDto.getCreatedDate()).lastModifiedDate(projectDto.getLastModifiedDate())
				.sdlcSystem(buildSdlcSystem(projectDto)).build();
		sdlcSystemRepository.findById(systemId).ifPresent(project::setSdlcSystem);
        return projectRepository.save(project);
	}
	
	public Project updateProject(Long projectId,ProjectDto projectDetails) {
		return projectRepository.findById(projectId).map(project -> {
                    project.setExternalId(projectDetails.getExternalId());
                    project.setName(projectDetails.getName());
                    project.setSdlcSystem(buildSdlcSystem(projectDetails));
                    final Project updatedProject = projectRepository.save(project);
                    return updatedProject;
                }).orElse(null);
        
    }
	private SdlcSystem buildSdlcSystem(ProjectDto projectDetails) {
	 return SdlcSystem.builder().baseUrl(projectDetails.getSdlcSystem().getBaseUrl())
			.description(projectDetails.getSdlcSystem().getDescription())
			.createdDate(projectDetails.getSdlcSystem().getCreatedDate())
			.lastModifiedDate(projectDetails.getSdlcSystem().getLastModifiedDate()).build();
}
	
}
