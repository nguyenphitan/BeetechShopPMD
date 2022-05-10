package com.nguyenphitan.BeetechAPI.service.admin;

import java.util.List;

import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;

public interface DiscountService {
	// Lấy tất cả discount:
	List<Discount> getAlls();
	
	// Lấy discount theo id:
	Discount getById(Long id);
	
	// Thêm mới discount:
	RedirectView createDiscount(Double discount, Double value);
	
	// Sửa theo id:
	Discount update(Long id, Discount discount);
	
	// Xóa theo id:
	void delete(Long id);
	
}
