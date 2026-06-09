package com.ordermanagement.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.ProductRequestDTO;
import com.ordermanagement.dto.ProductResponseDTO;
import com.ordermanagement.model.Product;
import com.ordermanagement.repository.ProductMapper;
import com.ordermanagement.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public Long createProduct(ProductRequestDTO request) {
		if(Objects.isNull(request)) {
			return null;
		}
		
		if(Objects.isNull(request.getPrice()) || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			return null;
		}
		
		if(Objects.isNull(request.getStock()) || request.getStock() < 0) {
			return null;
		}
		
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		
		productMapper.insertProduct(product);
		
		return product.getId();
			
	}

	@Override
	public List<ProductResponseDTO> getAllProducts() {
		List<Product> products = productMapper.getAllProducts();
	
		return products.stream()
				.map(this::toDTO)
				.toList();
	}

	@Override
	public List<ProductResponseDTO> getAllProducts(Integer page, Integer size) {
		if (Objects.isNull(page) || Objects.isNull(size)) {
			return getAllProducts();
		}
		
		if (page < 0 || size <= 0) {
			return null;
		}
		
		Integer offset = page * size;
		List<Product> products = productMapper.getAllProductsWithPagination(size, offset);
		
		return products.stream()
				.map(this::toDTO)
				.toList();
	}

	@Override
	public ProductResponseDTO getProductById(Long id) {
		if(Objects.isNull(id)) {
			return null;
		}
		
		Product product = productMapper.getProductById(id);
		
		if(Objects.isNull(product)) {
			return null;
		}
		
		return toDTO(product);
	}
	
	private ProductResponseDTO toDTO(Product product) {
		if (Objects.isNull(product)) {
			return null;
		}
		
		ProductResponseDTO responseDTO = new ProductResponseDTO(
			product.getId(),
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			product.getStock()
		);
		
		return responseDTO;
	}
}
