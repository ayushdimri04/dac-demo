package com.app.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@NonNull
	@NotBlank
	@Size(min=2,max = 15)
	private String firstName;

	@NonNull
	@NotBlank
	@Size(min=2,max = 15)
	private String lastName;
	
	private String role;
	
	private LocalDate hireDate;

	@Size(min=10,max = 10)
	private String phoneNumber;
}
