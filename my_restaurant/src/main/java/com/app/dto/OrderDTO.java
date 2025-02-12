package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class OrderDTO {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private Double price;
}
