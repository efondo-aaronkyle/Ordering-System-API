package com.ordermanagement.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ordermanagement.model.Order;
import com.ordermanagement.model.OrderItem;

@Mapper
public interface OrderMapper {
	public Long createOrder(Order order);
	public Long createOrderItem(OrderItem orderItem);
	public Order getOrderById(Long id);
	public List<OrderItem> getOrderItemsByOrderId(Long orderId);
	public List<Order> getCustomerOrders(Long customerId);
	public int cancelOrderById(Long id);
}
