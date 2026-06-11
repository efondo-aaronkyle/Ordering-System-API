package com.ordermanagement.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
		OrderResponseDTO createdOrder = orderService.createOrder(request);
		
		if (Objects.isNull(createdOrder)) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
		OrderResponseDTO order = orderService.getOrderById(id);
		
		if (Objects.isNull(order)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("customers/{id}/orders")
	public ResponseEntity<List<OrderResponseDTO>> getCustomerOrders(@PathVariable Long id) {
		List<OrderResponseDTO> orders = orderService.getCustomerOrders(id);
		
		return ResponseEntity.ok(orders);
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Void> cancelOrderById(@PathVariable Long id) {
		boolean cancelled = orderService.cancelOrderById(id);
		
		if (!cancelled) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
