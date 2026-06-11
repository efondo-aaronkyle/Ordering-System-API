package com.ordermanagement.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.CustomerRequestDTO;
import com.ordermanagement.dto.CustomerResponseDTO;
import com.ordermanagement.model.Customer;
import com.ordermanagement.repository.CustomerMapper;
import com.ordermanagement.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerMapper customerMapper; 
	
	@Override
	public CustomerResponseDTO registerCustomer(CustomerRequestDTO request) {
		if(isInvalidCustomerRequest(request)) {
			return null;
		}
		
		if(customerMapper.getCustomerByEmail(request.getEmail()) != null) {
			return null;
		}
		
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());
		
		customerMapper.registerCustomer(customer);
		
		return toDTO(customerMapper.getCustomerById(customer.getId()));
	}

	@Override
	public CustomerResponseDTO getCustomerById(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		
		return toDTO(customerMapper.getCustomerById(id));
	}
	
	private boolean isInvalidCustomerRequest(CustomerRequestDTO request) {
		return Objects.isNull(request)
			|| Objects.isNull(request.getEmail())
			|| request.getEmail().isBlank();
	}
	
	private CustomerResponseDTO toDTO(Customer customer) {
		if (Objects.isNull(customer)) {
			return null;
		}
		
		return new CustomerResponseDTO(
			customer.getId(),
			customer.getFirstName(),
			customer.getLastName(),
			customer.getEmail(),
			customer.getPhone(),
			customer.getCreatedAt()
		);
	}
}
