package com.app.services;

import java.util.List;

import com.app.dto.TimeSlotDTO;

public interface TableBookingSlotService {
	String addSlot(TimeSlotDTO slot);
	List<TimeSlotDTO> getAllSlots();
}
