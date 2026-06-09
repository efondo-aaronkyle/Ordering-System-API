package com.ordermanagement.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.OrderItemRequestDTO;
import com.ordermanagement.dto.OrderItemResponseDTO;
import com.ordermanagement.dto.OrderRequestDTO;
import com.ordermanagement.dto.OrderResponseDTO;
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

	@Override
	public Long createOrder(OrderRequestDTO request) {
		Order order = new Order();

		BigDecimal totalPrice = BigDecimal.ZERO;

		for (OrderItemRequestDTO item : request.getItems()) {
			Product product = productMapper.getProductById(item.getProductId());

			BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
			totalPrice = totalPrice.add(subtotal);
		}

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
		Order order = orderMapper.getOrderById(id);

		return this.toDTO(order);
	}

	@Override
	public List<OrderResponseDTO> getCustomerOrders(Long customerId) {
		List<Order> orders = orderMapper.getCustomerOrders(customerId);

		return orders.stream().map(
				order -> this.toDTO(order)
		).toList();
	}

	@Override
	public boolean cancelOrderById(Long id) {
		boolean result = false;

		Order order = orderMapper.getOrderById(id);

		if ("PENDING".equals(order.getStatus())) {
			List<OrderItem> items = orderMapper.getOrderItemsByOrderId(id);

			for (OrderItem item : items) {
				Product product = productMapper.getProductById(item.getProductId());
				product.setStock(product.getStock() + item.getQuantity());
				productMapper.updateProductStock(product);
			}

			if (orderMapper.cancelOrderById(id) > 0) {
				result = true;
			}
		}

		return result;
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
