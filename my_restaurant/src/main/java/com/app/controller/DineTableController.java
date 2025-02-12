package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DineTableDto;
import com.app.services.DineTableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/smartdine/dinetables")
@CrossOrigin("*")
public class DineTableController {
	
	@Autowired
	private DineTableService dineTableService;
	
	public DineTableController() {
		System.out.println("DineTableController Controller Started");
	}
	
	@GetMapping("/isavailable/{tableId}/{slot}")
	public ResponseEntity<?> available(@PathVariable Long tableId,@PathVariable Long slot){
		return ResponseEntity.ok(dineTableService.checkAvailability(tableId, slot));
	}
	
	@PostMapping
	public ResponseEntity<?> addTable(@Valid @RequestBody DineTableDto table){
		System.out.println(table);
		return ResponseEntity.status(HttpStatus.CREATED).body(dineTableService.addTable(table));
	}
	
	@GetMapping
	public ResponseEntity<?> getAllTable(){
		return ResponseEntity.ok(dineTableService.getAllTables());
	}
	
	@PutMapping("/{tableId}")
	public ResponseEntity<?> updateTable(@PathVariable Long tableId,@RequestBody DineTableDto table){
		return ResponseEntity.ok(dineTableService.updateTable(tableId, table));
	}
	
	@DeleteMapping("/{tableId}")
	public ResponseEntity<?> deleteTable(@PathVariable Long tableId){
		return ResponseEntity.ok(dineTableService.removeTable(tableId));
	}
	
	@PatchMapping("/removeslot/{tableId}/{slotId}")
	public ResponseEntity<?> updateTableBookStatus(@PathVariable Long tableId,@PathVariable Long slotId){
		return ResponseEntity.ok(dineTableService.updateTableBookStatus(tableId,slotId));
	}
	
	@GetMapping("/emptyslots")
	public ResponseEntity<?> emptyTableSlots(){
		return ResponseEntity.ok(dineTableService.emptyTableSlots());
	}
}

















