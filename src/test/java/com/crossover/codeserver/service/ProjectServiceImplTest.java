package com.crossover.codeserver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.crossover.codeserver.dao.CodeServerDAOImpl;
import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.dto.SdlcSystemDto;
import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.entities.SdlcSystem;
import com.crossover.codeserver.exception.ProjectNotFoundException;
import com.crossover.codeserver.services.ProjectServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

	@Spy
	ModelMapper modelMap = new ModelMapper();
		
	@Mock
	private CodeServerDAOImpl codeServerDAO;
	
	@InjectMocks
	ProjectServiceImpl projectService;
	
	
	
	@Test
	public void getProjecIdNotFound() {
		when(codeServerDAO.findProjectById(Mockito.any())).thenThrow(ProjectNotFoundException.class);
		assertThrows(ProjectNotFoundException.class, ()->{projectService.getProject(1123L);});
	}
	
	@Test
	public void getProject() {
		
		SdlcSystem s = new SdlcSystem(1L, "jira.com", "jira", null, null);
		Project p = Project.builder().id(2L).externalId("externalId").sdlcSystem(s).build();
		
		when(codeServerDAO.findProjectById(Mockito.any())).thenReturn(p);
		assertNotNull(projectService.getProject(2L));
	}
	
	@Test
	public void createProject() {
		
		Instant i = Instant.now();
		SdlcSystem s = SdlcSystem.builder().id(1L).baseUrl("jira.com").description("jira")
				.createdDate(i).lastModifiedDate(i).build();
		Project p = Project.builder().id(2L).externalId("externalId").sdlcSystem(s)
				.createdDate(i).lastModifiedDate(i).build();
		
		SdlcSystemDto sd = new SdlcSystemDto(1L, "jira.com", "jira", null, null);
		ProjectDto pd = ProjectDto.builder().id(2L).externalId("externalId").sdlcSystem(sd).build();
		
		when(codeServerDAO.saveProject(Mockito.any())).thenReturn(p);
		assertNotNull(projectService.createProject(pd));
	}
	
	@Test
	public void createProjectException() {
		
		Map<Object, Object> projectDtoFields = new HashMap<>();
		when(codeServerDAO.saveProject(Mockito.any())).thenThrow(ProjectNotFoundException.class);
		assertThrows(ProjectNotFoundException.class, ()->{projectService.patchProject(2L, projectDtoFields);});
	}
		
	@Test
	public void patchProjectException() {
		SdlcSystem s = new SdlcSystem(1L, "jira.com", "jira", null, null);
		Project p = Project.builder().id(2L).externalId("externalId").sdlcSystem(s).build();
		
		Map<Object, Object> projectDtoFields = new HashMap<>();
		when(codeServerDAO.findProjectById(Mockito.any())).thenReturn(p);
		when(codeServerDAO.saveProject(Mockito.any())).thenThrow(ProjectNotFoundException.class);
		assertThrows(ProjectNotFoundException.class, ()->{projectService.patchProject(2L, projectDtoFields);});
	}
	
	@Test
	public void patchProject() {
		
		SdlcSystem s = SdlcSystem.builder().id(1L).baseUrl("jira.com").description("jira")
				.createdDate(null).lastModifiedDate(null).build();
		Project p = Project.builder().id(2L).externalId("externalId").sdlcSystem(s)
				.createdDate(null).lastModifiedDate(null).build();
		Map<Object, Object> projectDtoFields = new HashMap<>();
		
		when(codeServerDAO.findProjectById(Mockito.any())).thenReturn(p);
		when(codeServerDAO.saveProject(Mockito.any())).thenReturn(p);
		assertNotNull(projectService.patchProject(2L, projectDtoFields));
	}
	
	
	@Test
	public void testSetFieldsToProject() {
		SdlcSystem s = new SdlcSystem(1L, "jira.com", "jira", null, null);
		Project p = Project.builder().id(2L).externalId("externalId").sdlcSystem(s).build();
		
		Map<Object, Object> projectDtoFields = new HashMap<>();
		projectDtoFields.put("externalId", "externalId2");
		projectDtoFields.put("id", null);
		
		projectService.setFieldsToProject(p, projectDtoFields);
		
		assertEquals("externalId2",p.getExternalId());
		assertNotNull(p.getSdlcSystem());
		assertEquals("jira.com",p.getSdlcSystem().getBaseUrl());
		assertEquals("jira",p.getSdlcSystem().getDescription());
	}
	
}
