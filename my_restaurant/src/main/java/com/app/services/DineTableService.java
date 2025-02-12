package com.app.services;

import java.util.List;

import com.app.dto.DineTableDto;

public interface DineTableService {
	String addTable(DineTableDto table);
	List<DineTableDto> getAllTables();
	String updateTable(Long tableId,DineTableDto table);
	String removeTable(Long tableId);
	String updateTableBookStatus(Long tableId);
	String updateTableBookStatus(Long tableId,Long slotId);
	boolean checkAvailability(Long tableId,Long slotId);
	String emptyTableSlots();
}
