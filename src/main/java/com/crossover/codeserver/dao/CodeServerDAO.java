package com.crossover.codeserver.dao;

import com.crossover.codeserver.entities.Project;


public interface CodeServerDAO {
	
	
	Project findProjectById(Long id);
	
	Project saveProject(Project project);

}
