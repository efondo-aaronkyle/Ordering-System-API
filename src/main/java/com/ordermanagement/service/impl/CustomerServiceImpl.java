package com.ordermanagement.service.impl;

import java.time.LocalDateTime;
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
		if(Objects.isNull(request)) {
			return null;
		}
		
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setEmail(request.getEmail());
		customer.setPhone(request.getPhone());
		
		customerMapper.registerCustomer(customer);
		
		Customer savedCustomer = customerMapper.getCustomerById(customer.getId());
		
		return this.toDTO(savedCustomer);
	}

	@Override
	public CustomerResponseDTO getCustomerById(Long id) {
		Customer customer = customerMapper.getCustomerById(id);
		
		if(Objects.isNull(customer)) {
			return null;
		}
		
		return this.toDTO(customer);
	}
	
	private CustomerResponseDTO toDTO(Customer customer) {
		CustomerResponseDTO responseDTO = new CustomerResponseDTO(
			customer.getId(),
			customer.getFirstName(),
			customer.getLastName(),
			customer.getEmail(),
			customer.getPhone(),
			customer.getCreatedAt()
		);
		
		return responseDTO;
	}
	
}
