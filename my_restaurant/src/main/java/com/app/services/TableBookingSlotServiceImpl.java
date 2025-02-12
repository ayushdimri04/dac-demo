package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.custom_exception.NoContentException;
import com.app.custom_exception.UniqueSlotException;
import com.app.dao.TableBookingSlotDao;
import com.app.dto.TimeSlotDTO;
import com.app.entities.TableBookingSlot;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class TableBookingSlotServiceImpl implements TableBookingSlotService {
	//Constructor Based Di (Mandatory)
	private TableBookingSlotDao slotDao;
	private ModelMapper mapper;


	@Override
	public String addSlot(TimeSlotDTO slot) {
		try {
			slot.setDayTime(slot.getDayTime().toUpperCase());
			TableBookingSlot newSlot = mapper.map(slot, TableBookingSlot.class);
			slotDao.save(newSlot);
			return "Slot Added Successfully";
		} catch (Exception e) {
			throw new UniqueSlotException("This Slot Is Already Available");
		}
	}

	@Override
	public List<TimeSlotDTO> getAllSlots() {
		try {
			List<TableBookingSlot> slots = slotDao.findAll();
			if(slots.size()==0) {
				throw new NoContentException("No Slots Found");	
			}
			return slots.stream().map((slot)->mapper.map(slot, TimeSlotDTO.class))
			.collect(Collectors.toList());
			
		} 
		catch (NoContentException e) {
			throw new NoContentException("No Slots Found");	
		}
		catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}
