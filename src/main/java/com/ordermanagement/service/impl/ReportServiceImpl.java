package com.ordermanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.SalesReportResponseDTO;
import com.ordermanagement.dto.TopProductResponseDTO;
import com.ordermanagement.repository.ReportMapper;
import com.ordermanagement.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportMapper reportMapper;

	@Override
	public SalesReportResponseDTO getSalesReport() {
		return reportMapper.getSalesReport();
	}

	@Override
	public List<TopProductResponseDTO> getTopSellingProducts() {
		return reportMapper.getTopSellingProducts();
	}
	
	
}
