package com.ordermanagement.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopProductResponseDTO {
	private Long productId;
	private String productName;
	private Long totalQuantitySold;
	private BigDecimal totalSales;
}
