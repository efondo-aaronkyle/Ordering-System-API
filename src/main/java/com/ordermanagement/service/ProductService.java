package com.ordermanagement.service;

import java.util.List;

import com.ordermanagement.dto.ProductRequestDTO;
import com.ordermanagement.dto.ProductResponseDTO;

public interface ProductService {
	
	Long createProduct(ProductRequestDTO product);
	List<ProductResponseDTO> getAllProducts();
	List<ProductResponseDTO> getAllProducts(Integer page, Integer size);
	ProductResponseDTO getProductById(Long id);
}
