package com.ordermanagement.service;

import java.util.List;

import com.ordermanagement.dto.SalesReportResponseDTO;
import com.ordermanagement.dto.TopProductResponseDTO;

public interface ReportService {
	
	public SalesReportResponseDTO getSalesReport();
	public List<TopProductResponseDTO> getTopSellingProducts();
	
}
