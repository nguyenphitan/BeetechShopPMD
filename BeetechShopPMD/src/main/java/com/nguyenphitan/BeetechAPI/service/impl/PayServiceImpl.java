package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.PayService;

@Service
public class PayServiceImpl implements PayService{

	@Autowired
	CartRepository cartRepository;
	
	@Autowired 
	ProductRepository productRepository;
	
	/*
	 * Thanh toán đơn hàng -> Reset giỏ hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void payment(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		// Xóa sản phẩm trong giỏ hàng
		// Update số lượng còn lại của mỗi sản phẩm
		List<CartResponse> listProducts = (List<CartResponse>) session.getAttribute("listProducts");
		
		for ( CartResponse cart : listProducts ) {
			Long idCard = cart.getId();
			Long idProduct = cart.getProduct().getId();
			cartRepository.deleteById(idCard);
			Product product = productRepository.getById(idProduct);
			Long quantity = product.getQuantity() - cart.getQuantity();
			product.setQuantity(quantity);
			productRepository.save(product);
		}
		
		// Xóa list sản phẩm khỏi session:
		session.removeAttribute("listProducts");
	}

}
