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
public class Product {
	
	private Long id;
	private String name; 
	private String description;
	private BigDecimal price;
	private Integer stock;
	private LocalDateTime createdAt;
	
}
