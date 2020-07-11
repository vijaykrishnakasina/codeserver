package com.crossover.codeserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.crossover.codeserver.controller.v2.ProjectRestController;
import com.crossover.codeserver.dto.ProjectDto;
import com.crossover.codeserver.dto.SdlcSystemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	void getProjectShouldReturn200() throws Exception {
		this.mockMvc.perform(get(ProjectRestController.ENDPOINT + "/1")).andExpect(status().isOk());
	}
	
	@Test
	void getProjectIllegalPathShouldReturn400() throws Exception {
		this.mockMvc.perform(get(ProjectRestController.ENDPOINT + "/whatever")).andExpect(status().isBadRequest());
	}
	
	@Test
	void getProjectInvalidPathShouldReturn400() throws Exception {
		this.mockMvc.perform(get(ProjectRestController.ENDPOINT + "/whatever")).andExpect(status().isBadRequest());
	}
	
	@Test
	void postProjectFullPayloadShouldReturn201() throws Exception {
		String payload = mapper.writeValueAsString(getProject("EXTERNALID", "Name", 2L));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isCreated());
	}
	
	@Test
	void postProjectPartialPayloadShouldReturn201() throws Exception {
		String payload = mapper.writeValueAsString(getProject("EXTERNAL-ID", null, 2L));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isCreated());
	}
	
	@Test
	void postProjectIllegalPayloadShouldReturn400() throws Exception {
		String payload = getProjectPayload("EXTERNAL-ID", null, "whatever");
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isBadRequest());
	}

	@Test
	void postProjectMissingEidShouldReturn400() throws Exception {
		String payload = mapper.writeValueAsString(getProject(null, null, 2L));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	void postProjectMissingSystemShouldReturn400() throws Exception {
		String payload = mapper.writeValueAsString(getProject("EXTERNAL-ID", null, null));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	void postProjectNonExistingSystemShouldReturn404() throws Exception {
		String payload = mapper.writeValueAsString(getProject("EXTERNAL-ID", null, 1234L));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isNotFound());
	}
	
	@Test
	void postProjectConflictingSystemShouldReturn409() throws Exception {
		String payload = mapper.writeValueAsString(getProject("SAMPLEPROJECT", null, 1L));
		this.mockMvc.perform(post(ProjectRestController.ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isConflict());
	}
	
	@Test
	void patchFullPayloadShouldReturn200() throws Exception {
		int project_id = 5;
		Long sys_id = 1L;
		
		String payload = getProjectPayload("EXTERNALIDEDITED", "Name-Edited", sys_id);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk());
	}
	
	@Test
	void patchPartialPayloadExtIdShouldReturn200() throws Exception {
		int project_id = 6;
		Long sys_id = null;
		
		String payload = getProjectPayload("EXTERNALIDEDITED", null , sys_id);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk());
	}
	
	@Test
	void patchPartialPayloadSysIdShouldReturn200() throws Exception {
		int project_id = 7;
		Long sys_id = 1L;
		
		String payload = getProjectPayload(null, null , sys_id);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk());
	}
	
	@Test
	void patchEmptyPayloadShouldReturn200() throws Exception {
		int project_id = 8;
		Long sys_id = null;
		
		String payload = getProjectPayload(null, null , sys_id);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk());
	}
	
	@Test
	void patchNullNamePayloadShouldReturn200() throws Exception {
		int project_id = 5;
		Map<String, String> map = new HashMap<>();
		map.put("name", null);
		String payload = mapper.writeValueAsString(map);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isOk());
	}
	
	@Test
	void patchIllegalSysIdShouldReturn400() throws Exception {
		int project_id = 1;
		
		String payload = getProjectPayload(null, null , "whatever");
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	void patchNonExistingSysIdShouldReturn404() throws Exception {
		int project_id = 1;
		
		String payload = getProjectPayload(null, null , 12345L);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isNotFound());
	}
	
	@Test
	void patchConflictingSysIdShouldReturn409() throws Exception {
		int project_id = 1;
		
		String payload = getProjectPayload(null, null , 2L);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isConflict());
	}
	
	@Test
	void patchConflictingExtIdShouldReturn409() throws Exception {
		int project_id = 1;
		
		String payload = getProjectPayload("PROJECTX", null , null);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isConflict());
	}
	
	@Test
	void patchConflictingExtIdAndSysIdShouldReturn409() throws Exception {
		int project_id = 1;
		
		String payload = getProjectPayload("PROJECTX", null , 2L);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+project_id).contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isConflict());
	}
	
	@Test
	void patchIllegalPathShouldReturn400() throws Exception {
		
		String payload = getProjectPayload(null, null , null);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+"whatever").contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isBadRequest());
	}
	
	@Test
	void patchInvalidPathShouldReturn400() throws Exception {
		
		String payload = getProjectPayload(null, null , null);
		this.mockMvc.perform(patch(ProjectRestController.ENDPOINT+"/"+"1234").contentType(MediaType.APPLICATION_JSON).content(payload))
					.andExpect(status().isNotFound());
	}
	
	private String getProjectPayload(String string, String object, Object id) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		if (string != null) map.put("externalId", string);
		if (object != null) map.put("name", object);
		if (id != null) map.put("sdlcSystem", getSDLCData(id));
		
		return mapper.writeValueAsString(map);
	}

	private Object getSDLCData(Object id) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		return map;
	}

	private ProjectDto getProject(String ext_id, String name, Long sdlc_id) {
		return ProjectDto.builder().externalId(ext_id).name(name).sdlcSystem(getValidSystem(sdlc_id)).build();
	}

	private SdlcSystemDto getValidSystem(Long id) {
		
		return SdlcSystemDto.builder().id(id).build();
	}
	
}
