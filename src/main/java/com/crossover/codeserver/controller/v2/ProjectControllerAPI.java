package com.crossover.codeserver.controller.v2;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.crossover.codeserver.dto.ProjectDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Project")
public interface ProjectControllerAPI {
	
	static final String API_PARAM_ID = "ID";
	public static final String ENDPOINT = "/api/v2/projects";
	public static final String ENDPOINT_ID = "/{id}";
	public static final String PATH_VARIABLE_ID = "id";

	@ApiOperation("Get a Project")
	@GetMapping(ENDPOINT_ID)
	ProjectDto getProject(@ApiParam(name = API_PARAM_ID, required = true)  @PathVariable(value = PATH_VARIABLE_ID, required = true) final Long projectId);
	
	@ApiOperation("Insert a Project")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProjectDto> createProject(@ApiParam(name = "project", required = true) @Valid @RequestBody ProjectDto project);

	@ApiOperation("Update a Project")
	@PatchMapping(ENDPOINT_ID)
	public ResponseEntity<Object> patchProject(@PathVariable(value = PATH_VARIABLE_ID, required = true) final Long projectId ,@Valid @RequestBody Map<Object, Object> project);
}
