package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Order;
import com.app.entities.OrderStatus;

public interface OrderDao extends JpaRepository<Order, Long>{
	List<Order> findByOrderStatus(OrderStatus status);
}
