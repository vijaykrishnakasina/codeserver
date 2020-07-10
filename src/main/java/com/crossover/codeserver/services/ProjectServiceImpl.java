package com.crossover.codeserver.services;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.dto.SdlcSystemDto;
import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.entities.SdlcSystem;
import com.crossover.codeserver.exception.ProjectNotFoundException;
import com.crossover.codeserver.exception.SystemNotFoundException;
import com.crossover.codeserver.repositories.ProjectRepository;
import com.crossover.codeserver.repositories.SdlcSystemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private SdlcSystemRepository sdlcSystemRepository;
	
	final ObjectMapper mappper = new ObjectMapper();

	public ProjectDto getProject(long id) {
		Project project =  projectRepository.findById(id).orElseThrow(RuntimeException::new);
		return convertProjectToProjectDto(project);
	}
	
	
	/**
	 * inserts project details into the database. 
	 * If incoming SDLC system is not present in the database, we will insert that SDLC system. 
	 * if SDLC system id is already present, we will not insert / update
	 */
	
	@Override
	public ProjectDto createProject(ProjectDto projectDto) {
		Project project = convertProjectDtoToProject(projectDto);
		sdlcSystemRepository.findById(project.getSdlcSystem().getId()).orElseThrow(() -> new SystemNotFoundException(project.getSdlcSystem().getId()));
		return convertProjectToProjectDto(projectRepository.save(project));
	}
	
	@Override
	public ProjectDto patchProject(long project_id, Map<Object, Object> projectDtoFields) {
		
		Project exising_project = projectRepository.findById(project_id).orElseThrow(() -> new ProjectNotFoundException(project_id));
		projectDtoFields.forEach((k,v)->{
			Field field = ReflectionUtils.findField(Project.class,(String) k);
			if (v instanceof String) {
				ReflectionUtils.setField(field, exising_project, v);
			}else {
				
				ReflectionUtils.setField(field, exising_project, mappper.convertValue(v, field.getType()));
			}
		});
		//sdlcSystemRepository.findById(project.getSdlcSystem().getId()).orElseThrow(() -> new SystemNotFoundException(project.getSdlcSystem().getId()));
		return convertProjectToProjectDto(exising_project);
	}
	
	
	
	//TODO: NPE
	private Project convertProjectDtoToProject(ProjectDto projectDto) {
		return Project.builder().externalId(projectDto.getExternalId()).name(projectDto.getName())
				.createdDate(projectDto.getCreatedDate()).lastModifiedDate(projectDto.getLastModifiedDate())
				.sdlcSystem(convertSdlcSystemDtoToSdlcSystem(projectDto.getSdlcSystem())).build();
	}
	
	private SdlcSystem convertSdlcSystemDtoToSdlcSystem(SdlcSystemDto sdlcSystemDto) {
		 return SdlcSystem.builder().id(sdlcSystemDto.getId()).baseUrl(sdlcSystemDto.getBaseUrl())
				.description(sdlcSystemDto.getDescription())
				.createdDate(sdlcSystemDto.getCreatedDate())
				.lastModifiedDate(sdlcSystemDto.getLastModifiedDate()).build();
	}
	
	private ProjectDto convertProjectToProjectDto(Project project) {
		return ProjectDto.builder().id(project.getId()).externalId(project.getExternalId()).name(project.getName())
				.createdDate(project.getCreatedDate()).lastModifiedDate(project.getLastModifiedDate())
				.sdlcSystem(convertSdlcSystemToSdlcSystemDto(project.getSdlcSystem())).build();
	}


	private SdlcSystemDto convertSdlcSystemToSdlcSystemDto(SdlcSystem sdlcSystem) {
		return SdlcSystemDto.builder().id(sdlcSystem.getId()).baseUrl(sdlcSystem.getBaseUrl())
				.description(sdlcSystem.getDescription())
				.createdDate(sdlcSystem.getCreatedDate())
				.lastModifiedDate(sdlcSystem.getLastModifiedDate()).build();
	}


	
}
