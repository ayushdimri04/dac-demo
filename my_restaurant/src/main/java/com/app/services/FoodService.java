package com.app.services;

import java.util.List;

import com.app.dto.FoodDTO;

public interface FoodService {
	
	String addFood(FoodDTO foodDto);
	String deleteFood(Long foodId);
	FoodDTO updateFood(Long foodId, FoodDTO foodDto);
	List<FoodDTO> getAllFood();
	
}
