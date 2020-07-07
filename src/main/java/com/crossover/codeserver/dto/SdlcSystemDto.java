package com.crossover.codeserver.dto;


import java.time.Instant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class SdlcSystemDto {

	private Long id;

	private String baseUrl;

	private String description;

	private Instant createdDate;

	private Instant lastModifiedDate;
}
