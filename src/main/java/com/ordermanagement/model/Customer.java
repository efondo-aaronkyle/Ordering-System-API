package com.ordermanagement.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDateTime createdAt;
	
}
