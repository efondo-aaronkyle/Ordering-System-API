package com.ordermanagement.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
		List<ProductResponseDTO> products;
		
		if(Objects.nonNull(page) && Objects.nonNull(size)) {
			products = productService.getAllProducts(page, size);
		} else {
			products = productService.getAllProducts();
		}
		
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
		ProductResponseDTO product = productService.getProductById(id);
		
		if (Objects.isNull(product)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(product);
	}
	
	@PostMapping("")
	public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO product) {
		ProductResponseDTO createdProduct = productService.createProduct(product);
		
		if (Objects.isNull(createdProduct)) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}
}
