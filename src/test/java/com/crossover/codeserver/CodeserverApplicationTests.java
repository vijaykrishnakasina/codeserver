package com.crossover.codeserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.crossover.codeserver.controller.v2.ProjectRestController;

@SpringBootTest
@AutoConfigureMockMvc
class CodeserverApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	
	@Test
	void getProjectShouldReturn200() throws Exception {
		this.mockMvc.perform(get(ProjectRestController.ENDPOINT + "/1")).andDo(print()).andExpect(status().isOk());
	}

}
