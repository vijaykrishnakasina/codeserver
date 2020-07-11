package com.crossover.codeserver.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crossover.codeserver.entities.Project;
import com.crossover.codeserver.exception.ConflictingDataException;
import com.crossover.codeserver.exception.ProjectNotFoundException;
import com.crossover.codeserver.exception.SystemNotFoundException;
import com.crossover.codeserver.repositories.ProjectRepository;
import com.crossover.codeserver.repositories.SdlcSystemRepository;

@Service
public class CodeServerDAOImpl implements CodeServerDAO {

	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	SdlcSystemRepository systemRepo;
	
	
	/**
	 * Searches project repository for the given project id. If project not found, throws exception.
	 */
	
	@Override
	public Project findProjectById(Long id) {
		
		return projectRepo.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
	}

	/**
	 * Saves / Updates project if all below criteria matches
	 * 
	 * 1. Given SdlcSystemId should already exists
	 * 2. There are no other records with combination of external id and system id
	 *  
	 */
	
	@Override
	public Project saveProject(Project project) {
		
		if (project == null || project.getSdlcSystem() == null || project.getSdlcSystem().getId() == null) {
			throw new IllegalArgumentException();
		}
		Long project_id = project.getId();
		Long sys_id = project.getSdlcSystem().getId();
		
		if (!systemRepo.existsById(sys_id))
			throw new SystemNotFoundException(sys_id);
		
		List<Project> existingRecords = projectRepo.findByExternalIdAndSdlcSystemId(project.getExternalId(), sys_id);
				
		if (existingRecords != null && existingRecords.stream().anyMatch(p-> (p.getId() != project_id))) {
			// there are existing records with same external id and system id apart from incoming project record
			throw new ConflictingDataException();
		}
		
		return projectRepo.save(project);
	}

}
