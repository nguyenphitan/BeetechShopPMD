package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;

/*
 * Service xử lý nghiệp vụ giỏ hàng khi chưa đăng nhập
 * Created by: NPTAN
 * Version: 1.0
 */
public interface CloneCartService {
	// Thêm mới sản phẩm vào giỏ hàng trên session:
	Cart addProductToCloneCart(ProductRequest productRequest, HttpServletRequest request);
	
	// Update số lượng sản phẩm trên giỏ hàng ảo session:
	Cart update(Long productId, Long quantityUpdate, HttpServletRequest request);
	
	// Xóa sản phẩm khỏi giỏ hàng trên session:
	void deleteProduct(Long productId, HttpServletRequest request);
	
}
