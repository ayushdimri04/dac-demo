package com.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tablebookingslot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableBookingSlot extends BaseEntity{
	@Column(length = 5,unique = true)
	private String bookingSlot;
	
	@Enumerated(EnumType.STRING)
	private TimeOfDay dayTime;
	
	@Override
	public boolean equals(Object obj) {
		TableBookingSlot tbt=(TableBookingSlot) obj;
		return this.getId().equals(tbt.getId());
	}
	
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
