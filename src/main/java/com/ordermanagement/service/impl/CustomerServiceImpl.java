package com.ordermanagement.service.impl;

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
	public Long registerCustomer(CustomerRequestDTO request) {
		Customer customer = new Customer();
			customer.setFirstName(request.getFirstName());
			customer.setLastName(request.getLastName());
			customer.setEmail(request.getEmail());
			customer.setPhone(request.getPhone());
			customer.setCreatedAt(request.getCreatedAt());
		
		Long id = customerMapper.registerCustomer(customer);
		return customer.getId();
	}

	@Override
	public CustomerResponseDTO getCustomerById(Long id) {
		Customer customer = customerMapper.getCustomerById(id);
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
