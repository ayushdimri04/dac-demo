package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderedFoodNameQuantityDTO {
	private Long id;
	private String name;
	private Long quantity;
	private String imageURL;
}
