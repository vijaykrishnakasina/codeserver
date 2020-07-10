
package com.crossover.codeserver.dto;


import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Builder
public class SdlcSystemDto {

	private Long id;

	@NotNull
	private String baseUrl;

	private String description;

	@NotNull
	private Instant createdDate;
	
	@NotNull
	private Instant lastModifiedDate;
}
