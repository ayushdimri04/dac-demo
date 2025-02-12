package com.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderFoodResponseDTO {
	private Long id;
	private Long tableNumber;
	private String tableLocation;
	private String reservationTime;
	private String bookedTime;
	private Double price;
	private String status;
	private List<OrderedFoodNameQuantityDTO> foods;
	private String imageURL;
	
}
