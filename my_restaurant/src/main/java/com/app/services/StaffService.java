package com.app.services;

import java.util.List;

import com.app.dto.StaffDTO;

public interface StaffService {

	String addStaff(StaffDTO staffDto);

	String deleteStaff(Long staffId);

	StaffDTO updateStaff(Long staffId, StaffDTO staffDto);

	List<StaffDTO> getAllStaff();
}
