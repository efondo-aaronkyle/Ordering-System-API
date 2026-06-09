package com.ordermanagement.service;

import com.ordermanagement.dto.CustomerRequestDTO;
import com.ordermanagement.dto.CustomerResponseDTO;

public interface CustomerService {
	CustomerResponseDTO registerCustomer(CustomerRequestDTO request);
	CustomerResponseDTO getCustomerById(Long id);
}