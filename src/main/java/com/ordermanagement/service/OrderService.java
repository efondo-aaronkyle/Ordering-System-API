package com.ordermanagement.service;

import java.util.List;

import com.ordermanagement.dto.OrderRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;

public interface OrderService {
	public Long createOrder(OrderRequestDTO request);
	public OrderResponseDTO getOrderById(Long id);
	public List<OrderResponseDTO> getCustomerOrders(Long customerId);
	public boolean cancelOrderById(Long id);
}
