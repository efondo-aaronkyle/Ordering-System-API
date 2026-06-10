package com.ordermanagement.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ordermanagement.dto.SalesReportResponseDTO;
import com.ordermanagement.dto.TopProductResponseDTO;

@Mapper
public interface ReportMapper {
	SalesReportResponseDTO getSalesReport();
	List<TopProductResponseDTO> getTopSellingProducts();
}