package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Food;
import com.app.entities.OrderedFood;

public interface OrderedFoodDao extends JpaRepository<OrderedFood, Long>{
	List<OrderedFood> findByFood(Food food);
}
