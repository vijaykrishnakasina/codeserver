package com.crossover.codeserver.controller.v2;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.services.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(ProjectRestController.ENDPOINT)
public class ProjectRestController implements ProjectControllerAPI{

	public static final String ENDPOINT = "/api/v2/projects";
	public static final String ENDPOINT_ID = "/{id}";
	public static final String PATH_VARIABLE_ID = "id";

	private static final String API_PARAM_ID = "ID";

	@Autowired
	private ProjectService projectService;

	@Override
	public ProjectDto getProject(long projectId) {
		return projectService.getProject(projectId);
	}
	
	
	
	public ResponseEntity<ProjectDto> createProject(ProjectDto project) {
		
		ProjectDto projectDto =  projectService.createProject(project);
		
		URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(projectDto.getId())
                .toUri();
		
		return ResponseEntity.created(location).body(projectDto);
		
	}
	
	public ResponseEntity<Object> patchProject(long projectId , Map<Object, Object> project) {
		
		ProjectDto projectDto =  projectService.patchProject(projectId, project);
		
		return new ResponseEntity<>(projectDto, HttpStatus.OK);
		
	}
}
