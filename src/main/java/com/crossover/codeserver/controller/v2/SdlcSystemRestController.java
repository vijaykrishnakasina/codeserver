package com.crossover.codeserver.controller.v2;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.repositories.ProjectRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "SDLC System")
public class SdlcSystemRestController {

    @Autowired
    private ProjectRepository projectRepository;

    @ApiOperation("Get a Project")
    @GetMapping("/api/v1/sdlc-systems/{systemId}/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable(value = "systemId") Long systemId,
                                              @PathVariable(value = "id") Long projectId) {
        return projectRepository.findBySdlcSystemIdAndId(systemId, projectId)
                .map(project -> ResponseEntity.ok().body(project))
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @ApiOperation("Update a Project")
    @PutMapping("/api/v1/sdlc-systems/{systemId}/projects/{id}")
    public Project updateProject(@PathVariable(value = "id") Long projectId, @Valid @RequestBody ProjectDto projectDetails){
    	return null;
    }
    
    
    
}
