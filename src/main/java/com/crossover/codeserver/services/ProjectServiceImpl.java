package com.crossover.codeserver.services;

import java.lang.reflect.Field;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.crossover.codeserver.dao.CodeServerDAO;
import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	CodeServerDAO codeServerDAO;
	
	@Autowired
	ModelMapper modelMap;
	
	final ObjectMapper mappper = new ObjectMapper();

	@Override
	public ProjectDto getProject(Long id) {
		
		Project project =  codeServerDAO.findProjectById(id);
		
		return modelMap.map(project, ProjectDto.class);
	}
	
	@Override
	public ProjectDto createProject(ProjectDto projectDto) {
		
		Project project = modelMap.map(projectDto, Project.class);
		
		project = codeServerDAO.saveProject(project);
		
		return modelMap.map(project, ProjectDto.class);
	}
	
	@Override
	public ProjectDto patchProject(Long project_id, Map<Object, Object> projectDtoFields) {
		
		Project project = null;
		
		Project exising_project = codeServerDAO.findProjectById(project_id);
		
		setFieldsToProject(exising_project, projectDtoFields);
		
		project = codeServerDAO.saveProject(exising_project);
		
		return modelMap.map(project, ProjectDto.class);
	}
	
	public void setFieldsToProject(Project exising_project, Map<Object, Object> projectDtoFields) {
		
		projectDtoFields.forEach((k,v)->{
			Field field = ReflectionUtils.findField(Project.class,(String) k);
			if (v instanceof String) {
				ReflectionUtils.setField(field, exising_project, v);
			}else {
				
				ReflectionUtils.setField(field, exising_project, mappper.convertValue(v, field.getType()));
			}
		});
		
	}

}
