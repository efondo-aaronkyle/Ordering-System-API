package com.ordermanagement.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordermanagement.dto.OrderItemRequestDTO;
import com.ordermanagement.dto.OrderItemResponseDTO;
import com.ordermanagement.dto.OrderRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;
import com.ordermanagement.model.Customer;
import com.ordermanagement.model.Order;
import com.ordermanagement.model.OrderItem;
import com.ordermanagement.model.Product;
import com.ordermanagement.repository.CustomerMapper;
import com.ordermanagement.repository.OrderMapper;
import com.ordermanagement.repository.ProductMapper;
import com.ordermanagement.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private ProductMapper productMapper;
	
	@Transactional
	@Override
	public Long createOrder(OrderRequestDTO request) {
		if(Objects.isNull(request)) {
			return null;
		}
		
		if(Objects.isNull(request.getCustomerId())) {
			return null;
		}
		
		if(Objects.isNull(request.getItems()) || request.getItems().isEmpty()) {
			return null;
		}
		
		Customer customer = customerMapper.getCustomerById(request.getCustomerId());
		
		if(Objects.isNull(customer)) {
			return null;
		}

		BigDecimal totalPrice = BigDecimal.ZERO;

		for (OrderItemRequestDTO item : request.getItems()) {
			if(Objects.isNull(item)) {
				return null;
			}
			
			if(Objects.isNull(item.getProductId())) {
				return null;
			}
			
			if(Objects.isNull(item.getQuantity()) || item.getQuantity() <= 0) {
				return null;
			}
			
			Product product = productMapper.getProductById(item.getProductId());
			
			if(Objects.isNull(product)) {
				return null;
			}
			
			if(product.getStock() < item.getQuantity()) {
				return null;
			}

			BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
			totalPrice = totalPrice.add(subtotal);
		}
		
		Order order = new Order();
		order.setCustomerId(request.getCustomerId());
		order.setTotalPrice(totalPrice);
		order.setStatus("PENDING");

		orderMapper.createOrder(order);

		for (OrderItemRequestDTO item : request.getItems()) {
			Product product = productMapper.getProductById(item.getProductId());

			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(order.getId());
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(product.getPrice());
			orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

			orderMapper.createOrderItem(orderItem);

			product.setStock(product.getStock() - item.getQuantity());
			productMapper.updateProductStock(product);
		}

		return order.getId();
	}

	@Override
	public OrderResponseDTO getOrderById(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		
		Order order = orderMapper.getOrderById(id);
		
		if(Objects.isNull(order)) {
			return null;
		}

		return this.toDTO(order);
	}

	@Override
	public List<OrderResponseDTO> getCustomerOrders(Long customerId) {
		if(Objects.isNull(customerId)) {
			return List.of();
		}
		
		Customer customer = customerMapper.getCustomerById(customerId);
		
		if(Objects.isNull(customer)) {
			return List.of();
		}
		
		List<Order> orders = orderMapper.getCustomerOrders(customerId);

		return orders.stream().map(
				order -> this.toDTO(order)
		).toList();
	}

	@Transactional
	@Override
	public boolean cancelOrderById(Long id) {
		if(Objects.isNull(id)) {
			return false;
		}

		Order order = orderMapper.getOrderById(id);
		
		if(Objects.isNull(order)) {
			return false;
		}
		
		if (!"PENDING".equals(order.getStatus())) {
			return false;
		}
		
		List<OrderItem> items = orderMapper.getOrderItemsByOrderId(id);

		for (OrderItem item : items) {
			Product product = productMapper.getProductById(item.getProductId());

			if (Objects.nonNull(product)) {
				product.setStock(product.getStock() + item.getQuantity());
				productMapper.updateProductStock(product);
			}
		}

		return orderMapper.cancelOrderById(id) > 0;
	}
	
	private OrderResponseDTO toDTO(Order order) {
		List<OrderItem> items = orderMapper.getOrderItemsByOrderId(order.getId());

		List<OrderItemResponseDTO> itemDTOs = items.stream().map(
			item -> new OrderItemResponseDTO(
				item.getId(),
				item.getOrderId(),
				item.getProductId(),
				item.getQuantity(),
				item.getPrice(),
				item.getSubtotal()
			)
		).toList();

		OrderResponseDTO responseDTO = new OrderResponseDTO(
			order.getId(),
			order.getCustomerId(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getCreatedAt(),
			itemDTOs
		);

		return responseDTO;
	}
}
