package com.ordermanagement.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	private Long id;
	private Long orderId;
	private Long productId;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal subtotal;
}
