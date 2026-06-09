package com.ordermanagement.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDateTime createdAt;
}