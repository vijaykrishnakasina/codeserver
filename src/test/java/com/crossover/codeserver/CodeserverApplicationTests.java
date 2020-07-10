package com.crossover.codeserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.crossover.codeserver.controller.v2.ProjectRestController;
import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.dto.SdlcSystemDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CodeserverApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void getProjectShouldReturn200() throws Exception {
		this.mockMvc.perform(get(ProjectRestController.ENDPOINT + "/1")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void putProjectShouldReturn200() throws Exception {
		ProjectDto projectDto = ProjectDto.builder().externalId("SAMPLEPROJECT1").name("Sample Project")
				.sdlcSystem(SdlcSystemDto.builder().id(2L).build()).build();
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(projectDto))).andDo(print()).andExpect(status().isOk());
	}

}
