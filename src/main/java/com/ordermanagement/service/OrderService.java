package com.ordermanagement.service;

import java.util.List;

import com.ordermanagement.dto.OrderRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;

public interface OrderService {
	
	OrderResponseDTO createOrder(OrderRequestDTO request);
	OrderResponseDTO getOrderById(Long id);
	List<OrderResponseDTO> getCustomerOrders(Long customerId);
	boolean cancelOrderById(Long id);
	
}
