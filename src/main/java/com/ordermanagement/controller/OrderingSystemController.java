package com.ordermanagement.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dto.CustomerRequestDTO;
import com.ordermanagement.dto.CustomerResponseDTO;
import com.ordermanagement.service.CustomerService;

@RestController
@RequestMapping("api")
public class OrderingSystemController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/customers")
	public Long registerCustomer(@RequestBody CustomerRequestDTO customer) {
		Long result = null;
		if (Objects.nonNull(customer)) {
			result = customerService.registerCustomer(customer);
		}
		return result;
	}
	
	@GetMapping("/customers/{id}")
		public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
		return customerService.getCustomerById(id);
	}
}