package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import com.app.dto.FoodOrderedDto;
import com.app.dto.ReservationDTO;
import com.app.services.OrderService;

@RestController
//@RequestMapping("/users/{userId}/orders")
@RequestMapping("/users/orders")
@CrossOrigin("*")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	public OrderController() {
		System.out.println("UserController Initailised");
	}
	
	@PostMapping
	public ResponseEntity<?> placeOrder(@RequestBody ReservationDTO reservation ){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		Long userId=(Long)authentication.getCredentials();
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderService.bookTable(userId, reservation));
	}
	
	//Get All Orders Of The User
	@GetMapping
	public ResponseEntity<?> getMyOrders(){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		Long userId=(Long)authentication.getCredentials();
		return ResponseEntity.ok(orderService.getOrderByUser(userId));
	}
	
	//Get User Order by orderid
	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrder(@PathVariable Long orderId){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		Long userId=(Long)authentication.getCredentials();
		return ResponseEntity.ok(orderService.cancelOrder(userId,orderId));
	}
	
	@PostMapping("/{orderId}/additem")
	public ResponseEntity<?> addNewFoodItemToOrder(@PathVariable Long orderId,
							@RequestBody FoodOrderedDto newFood){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		
		Long userId=(Long)authentication.getCredentials();
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderService.addFoodItemToOrder(userId, orderId, newFood));
	}
	
	@GetMapping("/{orderId}/removeitem/{foodId}")
	public ResponseEntity<?> removeFoodItemFromOrder(@PathVariable Long orderId,@PathVariable Long foodId){
		//Get UserId From UsernamePasswordAuthenticationToken Object Created After jwt token authentication
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("UserId = "+(Long)authentication.getCredentials());
		
		Long userId=(Long)authentication.getCredentials();
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderService.removeFoodItemFromOrder(orderId, foodId));
	}
}
