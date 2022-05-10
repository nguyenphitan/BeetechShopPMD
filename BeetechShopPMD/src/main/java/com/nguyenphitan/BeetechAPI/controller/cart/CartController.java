package com.nguyenphitan.BeetechAPI.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;

/**
 * Controller giỏ hàng
 * Created by: NPTAN (10/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;
	
	/*
	 * Thêm sản phẩm vào giỏ hàng
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PostMapping()
	public Cart addToCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request)  {
		return cartService.addToCart(productRequest, request);
	}

	
	/*
	 * Update số lượng sản phẩm
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PutMapping("/{id}")
	public Cart update(@PathVariable("id") Long id, @RequestBody Long quantityUpdate) {
		return cartService.update(id, quantityUpdate);
	}
	
	
	/*
	 * Xóa sản phẩm khỏi giỏ hàng
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		cartRepository.deleteById(id);
	}
	
}
