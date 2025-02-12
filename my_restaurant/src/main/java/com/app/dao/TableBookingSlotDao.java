package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.TableBookingSlot;

public interface TableBookingSlotDao extends JpaRepository<TableBookingSlot, Long>{
	TableBookingSlot findByBookingSlot(String bookingSlot);
}
