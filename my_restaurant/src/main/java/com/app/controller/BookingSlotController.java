package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TimeSlotDTO;
import com.app.services.TableBookingSlotService;

@RestController
@RequestMapping("/bookingslots")
@CrossOrigin("*")
public class BookingSlotController {
	@Autowired
	private TableBookingSlotService slotService;
	
	public BookingSlotController() {
		System.out.println("BookingSlotController");
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllSlots(){
		return ResponseEntity.ok(slotService.getAllSlots());
	}
	
	@PostMapping("/addslot")
	public ResponseEntity<?> addSlot(@RequestBody TimeSlotDTO slot){
		return ResponseEntity.status(HttpStatus.CREATED).body(slotService.addSlot(slot));
	}
	
	

}
