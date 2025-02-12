package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.StaffDTO;
import com.app.services.StaffService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/staffs")
@AllArgsConstructor
public class StaffController {

	@Autowired
	private StaffService staffService;

	public StaffController() {
		System.out.println("StaffController Initailised As A Bean");
	}

	@PostMapping("/addStaff")
	public ResponseEntity<?> addStaffController(@RequestBody StaffDTO staffDto) {
		System.out.println("In addStaffController: " + getClass());
		return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(staffDto));
	}

	@DeleteMapping("/deleteStaff/{staffId}")
	public ResponseEntity<?> deleteStaffController(@PathVariable Long staffId) {
		System.out.println("In deleteStaffController: " + getClass());
		return ResponseEntity.status(HttpStatus.OK).body(staffService.deleteStaff(staffId));
	}

	@PutMapping("/updateStaff/{staffId}")
	public ResponseEntity<?> updateStaffController(@PathVariable Long staffId, @RequestBody StaffDTO staffDto) {
		System.out.println("In updateStaffController: " + getClass());
		return ResponseEntity.ok(staffService.updateStaff(staffId, staffDto));
	}

	@GetMapping("/getAllStaff")
	public ResponseEntity<?> getAllStaffController() {
		System.out.println("In getAllStaffController: " + getClass());
		return ResponseEntity.ok(staffService.getAllStaff());
	}
}
