package com.app.dto;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	private Long tableId;
	
	private Long bookingSlotId;
	
	private List<FoodOrderedDto> foodDtoList;
	
	public ReservationDTO() {
		// TODO Auto-generated constructor stub
	}

}
