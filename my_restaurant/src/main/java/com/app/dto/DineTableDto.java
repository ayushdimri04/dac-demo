package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DineTableDto {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@NonNull
	@Min(1)
	private Long tableNumber;
	
	@NonNull
	@Min(1)
	private Long seatingCapacity;
	
	@NonNull
	@NotBlank
	private String location;
	
	private String status;
}
