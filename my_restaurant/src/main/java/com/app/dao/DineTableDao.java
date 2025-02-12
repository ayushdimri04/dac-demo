package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.DineTable;


public interface DineTableDao extends JpaRepository<DineTable, Long>{

//	List<DineTable> findByRestaurantId(Long restaurantId);
}
