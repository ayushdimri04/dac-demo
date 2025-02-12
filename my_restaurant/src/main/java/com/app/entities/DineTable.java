package com.app.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dinetables")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DineTable extends BaseEntity {
	@Column(name = "table_number",unique = true)
	private Long tableNumber;

	private Long seatingCapacity;

	@Enumerated(EnumType.STRING)
	private TableStatus status;

	@Enumerated(EnumType.STRING)
	private DineLocation location;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<TableBookingSlot> bookingSlots=new HashSet<>();
	
	
	public void addSlot(TableBookingSlot slot) {
		bookingSlots.add(slot);
	}
	
	public void removeSlot(TableBookingSlot slot) {
		bookingSlots.remove(slot);
	}
}



















