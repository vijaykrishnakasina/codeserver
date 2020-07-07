package com.crossover.codeserver.controller.v2;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.repositories.ProjectRepository;
import com.crossover.codeserver.repositories.SdlcSystemRepository;
import com.crossover.codeserver.services.ProjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "SDLC System")
public class SdlcSystemRestController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SdlcSystemRepository sdlcSystemRepository;
    
    @Autowired
    private ProjectService projectService;

    @ApiOperation("Get a Project")
    @GetMapping("/api/v1/sdlc-systems/{systemId}/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable(value = "systemId") Long systemId,
                                              @PathVariable(value = "id") Long projectId) {
        return projectRepository.findBySdlcSystemIdAndId(systemId, projectId)
                .map(project -> ResponseEntity.ok().body(project))
                .orElseGet(() -> ResponseEntity.ok().build());
    }

    @ApiOperation("Create a Project")
    @PostMapping(value = "/api/v1/sdlc-systems/{systemId}/projects")
    public Project createProject(@PathVariable(value = "systemId") Long systemId, @Valid @RequestBody ProjectDto project) {
    	return projectService.createProject(systemId,project);
    }

//    @ApiOperation("Update a Project")
//    @PutMapping("/api/v1/sdlc-systems/{systemId}/projects/{id}")
//    public ResponseEntity<Project> updateProject(@PathVariable(value = "systemId") Long systemId,
//                                                 @PathVariable(value = "id") Long projectId,
//                                                 @Valid @RequestBody Project projectDetails) {
//        return projectRepository.findBySdlcSystemIdAndId(systemId, projectId)
//                .map(project -> {
//                    project.setExternalId(projectDetails.getExternalId());
//                    project.setName(projectDetails.getName());
//                    project.setSdlcSystem(projectDetails.getSdlcSystem());
//                    final Project updatedProject = projectRepository.save(project);
//                    return ResponseEntity.ok(updatedProject);
//                })
//                .orElseGet(() -> ResponseEntity.noContent().build());
//    }
    
    @ApiOperation("Update a Project")
    @PutMapping("/api/v1/sdlc-systems/{systemId}/projects/{id}")
    public Project updateProject(@PathVariable(value = "id") Long projectId, @Valid @RequestBody ProjectDto projectDetails){
    	return projectService.updateProject(projectId, projectDetails);
    }
    
    
    
}
