package com.crossover.codeserver.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.entities.SdlcSystem;
import com.crossover.codeserver.exception.ConflictingDataException;
import com.crossover.codeserver.exception.ProjectNotFoundException;
import com.crossover.codeserver.exception.SystemNotFoundException;
import com.crossover.codeserver.repositories.ProjectRepository;
import com.crossover.codeserver.repositories.SdlcSystemRepository;

@ExtendWith(MockitoExtension.class)
public class CodeServerDaoTest {

	@Mock
	private ProjectRepository projectRepository;
	
	@Mock
	private SdlcSystemRepository sdlcSystemRepository;
	
	@InjectMocks
	CodeServerDAOImpl dao;
	
	
	@Test
	public void testFindProjectById() {
		Project p = Project.builder().id(2L).build();
		when(projectRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
		
		assertNotNull(dao.findProjectById(1L));
	}
	
	@Test
	public void testFindNonExistingProjectById() {
		when(projectRepository.findById(Mockito.any())).thenThrow(ProjectNotFoundException.class);
		assertThrows(ProjectNotFoundException.class, ()->{dao.findProjectById(2L);});
	}
	
	@Test
	public void testSaveProjectInvalidIp() {
		Project p = Project.builder().id(2L).build();
		SdlcSystem s = SdlcSystem.builder().id(null).build();
		assertThrows(IllegalArgumentException.class, ()->{dao.saveProject(null);});
		assertThrows(IllegalArgumentException.class, ()->{dao.saveProject(p);});
		p.setSdlcSystem(s);
		assertThrows(IllegalArgumentException.class, ()->{dao.saveProject(p);});
		s.setId(4L);
		
	}
	
	@Test
	public void testSaveProjectNonExistingSys() {
		Project p = Project.builder().id(2L).build();
		SdlcSystem s = SdlcSystem.builder().id(3L).build();
		p.setSdlcSystem(s);
		
		when(sdlcSystemRepository.existsById(Mockito.any())).thenReturn(Boolean.FALSE);
		assertThrows(SystemNotFoundException.class, ()->{dao.saveProject(p);});
	}
	
	@Test
	public void testSaveProjectNonExistingSysaa() {
		Project p = Project.builder().id(2L).build();
		SdlcSystem s = SdlcSystem.builder().id(3L).build();
		p.setSdlcSystem(s);
		
		when(sdlcSystemRepository.existsById(Mockito.any())).thenReturn(Boolean.TRUE);
		when(projectRepository.findByExternalIdAndSdlcSystemId(Mockito.any(), Mockito.any())).thenReturn(null);
		dao.saveProject(p);
		verify(projectRepository, times(1)).save(p);
		
		
		List<Project> records = new ArrayList<>();
		records.add(p);
		dao.saveProject(p);
		verify(projectRepository, times(2)).save(p);
		
		records.add(Project.builder().id(23L).build());
		when(projectRepository.findByExternalIdAndSdlcSystemId(Mockito.any(), Mockito.any())).thenReturn(records);
		
		assertThrows(ConflictingDataException.class, ()->{dao.saveProject(p);});
		
	}
	
}
