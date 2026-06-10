package com.ordermanagement.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ordermanagement.model.Product;

@Mapper
public interface ProductMapper {
	Long insertProduct(Product product);
	List<Product> getAllProducts();
	List<Product> getAllProductsWithPagination(
			@Param("limit") Integer limit,
			@Param("offset") Integer offset
	);
	Product getProductById(Long id);
	int updateProductStock(Product product);
}
