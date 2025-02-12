package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.FoodDTO;
import com.app.services.FoodService;

@RestController//@Controller + @ResponseBody
@RequestMapping("/food")
@CrossOrigin("*")
public class FoodController {
	@Autowired
	private FoodService foodService;

	public FoodController() {
		System.out.println("FoodController Instantiated");
	}
	
	@PostMapping("/addfood")
	public ResponseEntity<?> addFoodController(@RequestBody FoodDTO foodDto) {
		System.out.println("In addFoodController: "+getClass());
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(foodService.addFood(foodDto));
	}
	
	@DeleteMapping("/deleteFood/{foodId}")
	public ResponseEntity<?> deleteFoodController(@PathVariable Long foodId) {
		System.out.println("In deleteFoodController: "+getClass());
		return ResponseEntity.status(HttpStatus.OK).body(foodService.deleteFood(foodId));
	}
	
	@PutMapping("/updateFood/{foodId}")
	public ResponseEntity<?> updateFoodController(@PathVariable Long foodId,@RequestBody FoodDTO foodDto) {
		System.out.println("In updateFoodController: "+getClass());
		return ResponseEntity.ok(foodService.updateFood(foodId, foodDto));
	}
	
	@GetMapping("/getAllFood")
	public ResponseEntity<?> getAllFoodController(){
		System.out.println("In getAllFoodController: "+getClass());
		return ResponseEntity.ok(foodService.getAllFood());
	}
}










