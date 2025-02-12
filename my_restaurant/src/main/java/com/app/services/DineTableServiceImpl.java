package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.custom_exception.NoContentException;
import com.app.custom_exception.ResourceNotFound;
import com.app.custom_exception.TableSlotNotAvailableException;
import com.app.dao.DineTableDao;
import com.app.dao.TableBookingSlotDao;
import com.app.dto.DineTableDto;
import com.app.entities.DineLocation;
import com.app.entities.DineTable;
import com.app.entities.TableBookingSlot;
import com.app.entities.TableStatus;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class DineTableServiceImpl implements DineTableService {
	// Constructor based DI (mandatory)
	private ModelMapper mapper;
	private DineTableDao tableDao;
	private TableBookingSlotDao slotDao;

	@Override
	public String addTable(DineTableDto table) {
		try {
			table.setLocation(table.getLocation().toUpperCase());
			table.setStatus("OPEN");
			System.out.println(table);
			tableDao.save(mapper.map(table, DineTable.class));
			return "Table Added";
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Internal Exception");
		}
	}

	@Override
	public List<DineTableDto> getAllTables() {
		try {
			List<DineTableDto> tables = tableDao.findAll().stream()
					.map((table) -> mapper.map(table, DineTableDto.class)).collect(Collectors.toList());

			if (tables.size() == 0) {
				throw new NoContentException("No Tables Added");
			}
			return tables;
		} catch (Exception e) {
			throw new NoContentException("No Tables Added");
		}
	}

	@Override
	public String updateTable(Long tableId, DineTableDto table) {
		try {
			DineTable tablePersistent = tableDao.findById(tableId)
					.orElseThrow(() -> new ResourceNotFound("No Table Found With Id " + tableId));
			tablePersistent.setLocation(DineLocation.valueOf(table.getLocation().toUpperCase()));
			tablePersistent.setSeatingCapacity(table.getSeatingCapacity());
			tablePersistent.setTableNumber(table.getTableNumber());
			tablePersistent.setStatus(TableStatus.valueOf(table.getStatus().toUpperCase()));
			tableDao.save(tablePersistent);
			return "Table Details Updated Successfully";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String removeTable(Long tableId) {
		try {
			DineTable tablePersistent = tableDao.findById(tableId)
					.orElseThrow(() -> new ResourceNotFound("No Table Found With Id " + tableId));
			tableDao.delete(tablePersistent);
			return "Table Removed Successfully";
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String updateTableBookStatus(Long tableId) {
		try {
	
			DineTable tablePersistent = tableDao.findById(tableId)
					.orElseThrow(() -> new ResourceNotFound("No Table Found With Id " + tableId));
			
			if(tablePersistent.getStatus()==TableStatus.RESERVED)
				tablePersistent.setStatus(TableStatus.OPEN);
			else
				tablePersistent.setStatus(TableStatus.RESERVED);
			tableDao.save(tablePersistent);
			return "Table Status Updated to "+tablePersistent.getStatus();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public boolean checkAvailability(Long tableId, Long slotId) {
		String message="";
		try {
			DineTable table = tableDao.findById(tableId)
					.orElseThrow(() -> new ResourceNotFound("No Table Found With Id " + tableId));
			//Find Slot
			TableBookingSlot slotEntity = slotDao.findById(slotId)
					.orElseThrow(()->new ResourceNotFound("Slot Not Found"));
			//Check Table Availability
			if(table.getBookingSlots().contains(slotEntity)) {
				throw new TableSlotNotAvailableException("Table Not Available At Given Time");
			}
		}
		catch(ResourceNotFound e) {
			throw new ResourceNotFound(e.getMessage());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}

	@Override
	public String updateTableBookStatus(Long tableId, Long slotId) {
		try {
			DineTable table = tableDao.findById(tableId)
					.orElseThrow(() -> new ResourceNotFound("No Table Found With Id " + tableId));
			//Find Slot
			TableBookingSlot slotEntity = slotDao.findById(slotId)
					.orElseThrow(()->new ResourceNotFound("Slot Not Found"));
			//table removed from slot
			table.getBookingSlots().remove(slotEntity);
			return "Success";
		} 
		catch(ResourceNotFound e) {
			throw new ResourceNotFound(e.getMessage());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String emptyTableSlots() {
		try {
			List<DineTable> tables = tableDao.findAll();
			tables.stream().forEach((table)->table.getBookingSlots().clear());
			return "All Slots Are Empty Now";
		} catch (Exception e) {
			throw new RuntimeException("Emptying Slots Failed");
		}
	}

}
