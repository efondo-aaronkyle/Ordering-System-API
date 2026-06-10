package com.ordermanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
	private Long id;
	private Long customerId;
	private BigDecimal totalPrice;
	private String status;
	private LocalDateTime createdAt;
	
	private List<OrderItemResponseDTO> items;
}
