package com.app.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custom_exception.NoContentException;
import com.app.custom_exception.ResourceNotFound;
import com.app.custom_exception.ResourceNotFoundException;
import com.app.dao.StaffDao;
import com.app.dto.StaffDTO;
import com.app.entities.Staff;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public String addStaff(StaffDTO staffDto) {
		// TODO Auto-generated method stub
		try {

			staffDao.save(modelMapper.map(staffDto, Staff.class));
			return "Staff Added successfully!!";
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Unable to add Staff!");
		}
	}

	@Override
	public String deleteStaff(Long staffId) {
		// TODO Auto-generated method stub
		try {

			if (staffDao.existsById(staffId)) {
				staffDao.deleteById(staffId);
				return "Staff deleted successfully!";
			}

		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFound("Unable to delete food!");
		}
		throw new ResourceNotFound("Unable to delete food!");
	}

	@Override
	public StaffDTO updateStaff(Long staffId, StaffDTO staffDto) {
		// TODO Auto-generated method stub
		try {

			if (staffDao.existsById(staffId)) {
				Staff staff = staffDao.findById(staffId).orElseThrow(() -> new ResourceNotFound("Staff Not Found"));
				staff.setFirstName(staffDto.getFirstName());
				staff.setLastName(staffDto.getLastName());
				staff.setPhoneNumber(staffDto.getPhoneNumber());
				staff.setHireDate(staffDto.getHireDate());
				return modelMapper.map(staff, StaffDTO.class);
			} else {
				throw new ResourceNotFound("Resource Not Found Exception");
			}
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new ResourceNotFoundException("Unable to add food!");
		}
	}

	@Override
	public List<StaffDTO> getAllStaff() {
		// TODO Auto-generated method stub
		try {

			List<Staff> staffList = staffDao.findAll();
			List<StaffDTO> staffListDto = new ArrayList<>();
			for (Staff s : staffList) {
				staffListDto.add(modelMapper.map(s, StaffDTO.class));
			}

			return staffListDto;

		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new NoContentException("Unable to find food!");
		}
	}
}
