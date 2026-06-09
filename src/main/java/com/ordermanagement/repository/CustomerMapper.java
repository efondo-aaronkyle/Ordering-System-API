package com.ordermanagement.repository;

import org.apache.ibatis.annotations.Mapper;

import com.ordermanagement.model.Customer;

@Mapper
public interface CustomerMapper {

	public Customer getCustomerById(Long id);
	public Long registerCustomer(Customer customer);
}
