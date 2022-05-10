package com.nguyenphitan.BeetechAPI.service.admin;

import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.User;

public interface SellerService {
	// Lấy tất cả các account có role là SELLER
	List<User> getAllSellers();
	
	// Thêm Seller:
	int addSeller(int id);
	
	// Xóa Seller:
	void deleteSeller(int id);
	
}
