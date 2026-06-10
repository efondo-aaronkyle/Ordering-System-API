package com.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dto.OrderRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;
import com.ordermanagement.service.OrderService;

@RestController
@RequestMapping("api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping("/orders")
	public Long createOrder(@RequestBody OrderRequestDTO request) {
		return orderService.createOrder(request);
	}

	@GetMapping("/orders/{id}")
	public OrderResponseDTO getOrderById(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}
	
	@GetMapping("customers/{id}/orders")
	public List<OrderResponseDTO> getCustomerOrders(@PathVariable Long id) {
		return orderService.getCustomerOrders(id);
	}

	@DeleteMapping("/orders/{id}")
	public boolean cancelOrderById(@PathVariable Long id) {
		return orderService.cancelOrderById(id);
	}
}
