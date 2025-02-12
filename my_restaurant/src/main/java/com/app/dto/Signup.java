package com.app.dto;

import com.app.entities.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Signup {
	@JsonProperty(access = Access.READ_ONLY) // this property only used during ser.
	private Long id;
	@NotBlank(message = "First Name required")
	private String firstName;
	private String lastName;
	@Email(message = "Invalid Email!!!")
	private String email;
	
	@Column(length = 10)
	private String phoneNumber;
	
	@Column(length=50)
	private String address;
	
	@JsonProperty(access = Access.WRITE_ONLY)// this property only used during de-ser.
	private String password;
	
	@JsonProperty(access = Access.WRITE_ONLY)// this property only used during de-ser.
	private String confirmPassword;
	
	private UserRole role;
	public Signup(String firstName, String lastName,
			String email, String password, UserRole role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	
}
