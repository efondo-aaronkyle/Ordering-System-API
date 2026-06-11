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
	public OrderResponseDTO createOrder(OrderRequestDTO request) {
		if(isInvalidOrderRequest(request)) {
			return null;
		}
		
		Customer customer = customerMapper.getCustomerById(request.getCustomerId());
		
		if(Objects.isNull(customer)) {
			return null;
		}
		
		BigDecimal totalPrice = calculateTotalPrice(request.getItems());
		
		if(Objects.isNull(totalPrice)) {
			return null;
		}
		
		Order order = new Order();
		order.setCustomerId(request.getCustomerId());
		order.setTotalPrice(totalPrice);
		order.setStatus("PENDING");
		
		orderMapper.createOrder(order);
		saveOrderItemsAndDeductStock(order.getId(), request.getItems());
		
		return getOrderById(order.getId());
	}

	@Override
	public OrderResponseDTO getOrderById(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		
		return toDTO(orderMapper.getOrderById(id));
	}

	@Override
	public List<OrderResponseDTO> getCustomerOrders(Long customerId) {
		if(Objects.isNull(customerId)) {
			return List.of();
		}
		
		if(Objects.isNull(customerMapper.getCustomerById(customerId))) {
			return List.of();
		}
		
		return orderMapper.getCustomerOrders(customerId)
				.stream()
				.map(this::toDTO)
				.toList();
	}

	@Transactional
	@Override
	public boolean cancelOrderById(Long id) {
		if(Objects.isNull(id)) {
			return false;
		}
		
		Order order = orderMapper.getOrderById(id);
		
		if(order == null || !"PENDING".equals(order.getStatus())) {
			return false;
		}
		
		restoreProductStock(id);
		
		return orderMapper.cancelOrderById(id) > 0;
	}
	
	private boolean isInvalidOrderRequest(OrderRequestDTO request) {
		return Objects.isNull(request)
				|| Objects.isNull(request.getCustomerId())
				|| Objects.isNull(request.getItems())
				|| request.getItems().isEmpty();
	}
	
	private boolean isInvalidOrderItem(OrderItemRequestDTO item) {
		return Objects.isNull(item)
				|| Objects.isNull(item.getProductId())
				|| Objects.isNull(item.getQuantity())
				|| item.getQuantity() <= 0;
	}
	
	private BigDecimal calculateTotalPrice(List<OrderItemRequestDTO> items) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		for (OrderItemRequestDTO item : items) {
			if (isInvalidOrderItem(item)) {
				return null;
			}
			
			Product product = productMapper.getProductById(item.getProductId());
			
			if (product == null || product.getStock() < item.getQuantity()) {
				return null;
			}
			
			BigDecimal subtotal = product.getPrice()
					.multiply(BigDecimal.valueOf(item.getQuantity()));
			
			totalPrice = totalPrice.add(subtotal);
		}
		
		return totalPrice;
	}
	
	private void saveOrderItemsAndDeductStock(Long orderId, List<OrderItemRequestDTO> items) {
		for (OrderItemRequestDTO item : items) {
			Product product = productMapper.getProductById(item.getProductId());
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			orderItem.setProductId(item.getProductId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(product.getPrice());
			orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
			
			orderMapper.createOrderItem(orderItem);
			
			product.setStock(product.getStock() - item.getQuantity());
			productMapper.updateProductStock(product);
		}
	}
	
	private void restoreProductStock(Long orderId) {
		List<OrderItem> items = orderMapper.getOrderItemsByOrderId(orderId);
		
		for(OrderItem item : items) {
			Product product = productMapper.getProductById(item.getProductId());
			
			if(Objects.nonNull(product)) {
				product.setStock(product.getStock() + item.getQuantity());
				productMapper.updateProductStock(product);
			}
		}
	}
	
	private OrderResponseDTO toDTO(Order order) {
		if (Objects.isNull(order)) {
			return null;
		}
		
		List<OrderItem> items = orderMapper.getOrderItemsByOrderId(order.getId());
		
		List<OrderItemResponseDTO> itemDTOs = items.stream()
				.map(item -> new OrderItemResponseDTO(
						item.getId(),
						item.getOrderId(),
						item.getProductId(),
						item.getQuantity(),
						item.getPrice(),
						item.getSubtotal()
				))
				.toList();
		
		return new OrderResponseDTO(
			order.getId(),
			order.getCustomerId(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getCreatedAt(),
			itemDTOs
		);
	}
}
