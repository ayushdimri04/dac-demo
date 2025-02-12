package com.app.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "staffs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Staff extends BaseEntity {
	@Column(length=20)
	private String firstName;
	
	@Column(length=20)
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private StaffRole role;

	@Column(length=10)
	private String phoneNumber;

	private LocalDate hireDate;

}