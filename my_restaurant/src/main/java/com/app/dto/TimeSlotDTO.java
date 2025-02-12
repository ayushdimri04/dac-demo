package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSlotDTO {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@NotNull
	@NotBlank
	private String bookingSlot;
	
	@NotNull
	@NotBlank
	private String dayTime;
}
