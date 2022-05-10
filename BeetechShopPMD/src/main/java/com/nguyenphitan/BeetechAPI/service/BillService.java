package com.nguyenphitan.BeetechAPI.service;

import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;

public interface BillService {
	// Hiển thị danh sách tất cả hóa đơn:
	ModelAndView billManagerPage();
	
	// Tạo hóa đơn:
	OrderAccount create(BillRequest billRequest);
	
	// Cập nhật hóa đơn:
	OrderAccount update(Long id);
}
