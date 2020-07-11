package com.crossover.codeserver.dto;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;

	@NotNull
	private String externalId;

	private String name;

	@NotNull
	private SdlcSystemDto sdlcSystem;

	private Instant createdDate;

	private Instant lastModifiedDate;
}
