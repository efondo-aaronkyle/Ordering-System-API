package com.ordermanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	private Long id;
	private Long customerId;
	private BigDecimal totalPrice;
	private String status;
	private LocalDateTime createdAt;
	
}
