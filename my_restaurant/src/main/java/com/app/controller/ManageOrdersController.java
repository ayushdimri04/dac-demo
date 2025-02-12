package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.services.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class ManageOrdersController {
	@Autowired
	private OrderService orderService;
	
	public ManageOrdersController() {
		System.out.println("ManageOrdersController Controller Started");
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	@GetMapping("/{status}")
	public ResponseEntity<?> getAllOrdersByStatus(@PathVariable String status){
		return ResponseEntity.ok(orderService.getOrderByOrderStatus(status));
	}
	
}
