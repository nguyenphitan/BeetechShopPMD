package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;

public interface CartService {
	// Lấy tất cả sản phẩm trong giỏ hàng:
	ModelAndView getAllCart(String page, HttpServletRequest request);
	
	// Thêm vào giỏ hàng:
	Cart addToCart(ProductRequest productRequest, HttpServletRequest request);

	// Xử lý nghiệp vụ liên quan đến thanh toán:
	void handlePayment(ModelAndView modelAndView, List<CartResponse> listCartResponses, HttpServletRequest request);

	// Đếm số lượng giỏ hàng: (trả về số lượng loại sản phẩm)
	void countCartSize(HttpServletRequest request);
	
	// Update số lượng sản phẩm trong giỏ hàng:
	Cart update(Long id, Long quantityUpdate);
}
