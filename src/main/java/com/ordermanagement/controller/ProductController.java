package com.ordermanagement.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dto.ProductRequestDTO;
import com.ordermanagement.dto.ProductResponseDTO;
import com.ordermanagement.service.ProductService;

@RestController
@RequestMapping("api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public List<ProductResponseDTO> getAllProducts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		if(Objects.nonNull(page) && Objects.nonNull(size)) {
			return productService.getAllProducts(page, size);
		}	
		
		return productService.getAllProducts();
	}
	
	@GetMapping("/{id}")
	public ProductResponseDTO getProductById(@PathVariable Long id) {
		if(Objects.nonNull(id)) {
			return productService.getProductById(id);
		}
		
		return null;
	}
	
	@PostMapping("")
	public Long createProduct(@RequestBody ProductRequestDTO product) {
		Long result = null;
		
		if(Objects.nonNull(product)) {
			result = productService.createProduct(product);
		}
		
		return result;
	}
}
