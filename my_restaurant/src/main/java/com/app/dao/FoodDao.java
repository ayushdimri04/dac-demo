package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Food;

public interface FoodDao extends JpaRepository<Food, Long> {

}
