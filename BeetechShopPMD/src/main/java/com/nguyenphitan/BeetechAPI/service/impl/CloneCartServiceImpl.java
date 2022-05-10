package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.service.CloneCartService;

@Service
public class CloneCartServiceImpl implements CloneCartService {

	/*
	 * Thêm vào giỏ hàng session:
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart addProductToCloneCart(ProductRequest productRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> cartsSession = null;
		if( session.getAttribute("cartsSession") != null ) {
			cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			// update số lượng cho sản phẩm (Nếu sản phẩm đã có trong session):
			for(Cart cart : cartsSession) {
				if( cart.getIdProduct() == productRequest.getIdProduct() ) {
					cart.setQuantity( cart.getQuantity() + productRequest.getQuantitySelected() );
					session.setAttribute("cartsSession", cartsSession);
					return cart;
				}
			}
		}
		else {
			cartsSession = new ArrayList<Cart>();
		}
		// Thêm mới sản phẩm (Nếu sản phẩm không có trong session)
		Cart cart = new Cart();
		cart.setIdProduct(productRequest.getIdProduct());
		cart.setIdUser(-1L);
		cart.setQuantity(productRequest.getQuantitySelected());
		
		cartsSession.add(cart);
		session.setAttribute("cartsSession", cartsSession);
		return cart;
	}
	
	
	/*
	 * Update số lượng sản phẩm trên giỏ hàng ảo:
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart update(Long productId, Long quantityUpdate, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> carts = (List<Cart>) session.getAttribute("cartsSession");
		for(Cart cart : carts) {
			if( cart.getIdProduct() == productId ) {
				Long quantityCurrent = cart.getQuantity();
				cart.setQuantity(quantityCurrent + quantityUpdate);
				return cart;
			}
		}
		return null;
	}

	
	/*
	 * Xóa khỏi giỏ hàng session:
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void deleteProduct(Long productId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> carts = (List<Cart>) session.getAttribute("cartsSession");
		for(Cart cart : carts) {
			Long proId = cart.getIdProduct();
			if( productId == proId ) {
				carts.remove(cart);
				break;
			}
		}
	}

}
