package com.ordermanagement.service;

import com.ordermanagement.dto.CustomerRequestDTO;
import com.ordermanagement.dto.CustomerResponseDTO;

public interface CustomerService {
	public Long registerCustomer(CustomerRequestDTO request);
	public CustomerResponseDTO getCustomerById(Long id);
}