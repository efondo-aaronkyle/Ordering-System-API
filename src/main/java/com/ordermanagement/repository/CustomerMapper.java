package com.ordermanagement.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ordermanagement.model.Customer;

@Mapper
public interface CustomerMapper {

	Customer getCustomerById(Long id);
	Customer getCustomerByEmail(String email);
	Long registerCustomer(Customer customer);
	
}
