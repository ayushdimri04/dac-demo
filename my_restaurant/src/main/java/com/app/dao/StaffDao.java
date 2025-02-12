package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Staff;
import com.app.entities.StaffRole;

public interface StaffDao extends JpaRepository<Staff,Long>{
	Optional<Staff> findByRole(StaffRole role);
}
