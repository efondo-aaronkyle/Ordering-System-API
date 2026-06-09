package com.ordermanagement.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("")
	public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO customer) {
		if(Objects.isNull(customer)) {
			return ResponseEntity.badRequest().build();
		}
		
		CustomerResponseDTO createdCustomer = customerService.registerCustomer(customer);
		
		if (Objects.isNull(createdCustomer)) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
		CustomerResponseDTO customer = customerService.getCustomerById(id);
		
		if(Objects.isNull(customer)) {
			return ResponseEntity.notFound().build();
		}
			
		return ResponseEntity.ok(customer);
	}
}