package com.crossover.codeserver.dto;

import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProjectDto {

	private Long id;

	private String externalId;

	private String name;

	private SdlcSystemDto sdlcSystem;

	private Instant createdDate;

	private Instant lastModifiedDate;
}
