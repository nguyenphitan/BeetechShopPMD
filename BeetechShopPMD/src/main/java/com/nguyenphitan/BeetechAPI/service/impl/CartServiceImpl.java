package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	DiscountRepository discountRepository;
	
	
	/*
	 * Lấy ra tất cả các sản phẩm trong giỏ hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public ModelAndView getAllCart(String page, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView(page);
		
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		List<CartResponse> listProducts = new ArrayList<CartResponse>();
		
		// Nếu chưa có token -> get list cart từ session
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			// Nếu chưa có sản phẩm -> hiển thị chưa có sản phẩm nào:
			if( cartsSession == null ) {
				modelAndView.addObject("listProducts", null);
				session.setAttribute("listProducts", null);
				return modelAndView;
			}
			// Nếu có sản phẩm -> hiển thị danh sách sản phẩm:
			for(Cart cart: cartsSession) {
				Long idCart = cart.getId();
				Long idProduct = cart.getIdProduct();
				Long quantity = cart.getQuantity();
				Product product = productRepository.getById(idProduct);
				CartResponse cartResponse = new CartResponse(idCart, product, quantity);
				listProducts.add(cartResponse);
			}
		}
		else {	
			// Nếu đã có token -> get giỏ hàng từ database tương ứng với idUser:
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			User user = userRepository.getById(idUser);
			List<Cart> listCarts = cartRepository.findByIdUser(idUser);
			if( listCarts != null ) {
				for(Cart cart: listCarts) {
					Long idCart = cart.getId();
					Long idProduct = cart.getIdProduct();
					Long quantity = cart.getQuantity();
					Product product = productRepository.getById(idProduct);
					CartResponse cartResponse = new CartResponse(idCart, product, quantity);
					listProducts.add(cartResponse);
				}							
			}
			modelAndView.addObject("userInfo", user);

		
			// Chuyển user id lên session:
			session.setAttribute("idUser", idUser);
		
		}
		
		session.setAttribute("listProducts", listProducts);
		modelAndView.addObject("listProducts", listProducts);
		session.setAttribute("cartSize", listProducts.size());
		handlePayment(modelAndView, listProducts, request);
		
		return modelAndView;
	}

	
	/*
	 * Xử lý nghiệp vụ liên quan đến thanh toán:
	 * 1. Tính toán discount, giá trị discount
	 * 2. Tính toán giá trị sau khi trừ discount
	 * 3. Gợi ý discount tiếp theo
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void handlePayment(ModelAndView modelAndView, List<CartResponse> listCartResponses,
			HttpServletRequest request) {
		// Tính tổng tiền trong giỏ hàng -> gợi ý discount (DiscountService)
		Double totalCart = 0D;
		for(CartResponse cart : listCartResponses) {
			Long productPrice = cart.getProduct().getPrice();
			Long quantity = cart.getQuantity();
			Long totalPrice = quantity * productPrice;
			totalCart += totalPrice;
		}
		
		// Gợi ý discount:
		// 1. Lấy ra discount hiện tại: (nếu có)
		Double currentDiscount = 0D;
		Double nextDiscount = 0D;
		Double nextValue = 0D;
		List<Discount> discounts = discountRepository.findAll();
		discounts.sort(Comparator.comparing(Discount::getValue));
		
		if( totalCart < discounts.get(0).getValue() ) {
			nextDiscount = discounts.get(0).getDiscount();
			nextValue = discounts.get(0).getValue();
		} else {
			for ( int i = 1 ; i < discounts.size() ; i++ ) {
				if( totalCart >= discounts.get(i-1).getValue() && totalCart < discounts.get(i).getValue() ) {
					currentDiscount = discounts.get(i-1).getDiscount();
					nextDiscount = discounts.get(i).getDiscount();
					nextValue = discounts.get(i).getValue();
					break;
				}
			}
		}
		
		// 2. Tính toán lại tổng tiền sau khi trừ discount:
		Double discountValue = (totalCart * currentDiscount)/100;
		Long realCart = Math.round(totalCart - discountValue);
		
		// 3. Gợi ý mua thêm xx tiền để đạt discount tiếp theo:
		Double valueToNextDiscount = nextValue - totalCart; 
		
		modelAndView.addObject("currentDiscount", currentDiscount);
		modelAndView.addObject("nextDiscount", nextDiscount);
		modelAndView.addObject("discountValue", discountValue);
		modelAndView.addObject("valueToNextDiscount", valueToNextDiscount);
		modelAndView.addObject("totalCart", totalCart);
		modelAndView.addObject("realCart", realCart);
		
		// Đưa số tiền phải thanh toán lên session:
		HttpSession session = request.getSession();
		session.setAttribute("realCart", realCart);
		
	}

	/*
	 * Thêm sản phẩm vào giỏ hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart addToCart(ProductRequest productRequest, HttpServletRequest request) {
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		Long idProduct = productRequest.getIdProduct();
		Long quantitySelected = productRequest.getQuantitySelected();
		
		// Update số lượng sản phẩm trong giỏ hàng:
		// Tìm kiếm sản phẩm có id = id sản phẩm được thêm:
		// Nếu có -> update số lượng sản phẩm trong giỏ hàng (thêm) ứng với user id.
		// Nếu không có -> thêm mới 
		List<Cart> listCarts = cartRepository.findByIdProduct(idProduct);
		if( !listCarts.isEmpty() ) {
			for(Cart cart : listCarts) {
				if( cart.getIdUser() == idUser ) {
					Long quantity = cart.getQuantity() + quantitySelected;
					cart.setQuantity(quantity);
					return cartRepository.save(cart);
				}
			}
		}
		Cart cart = new Cart();
		cart.setIdProduct(idProduct);
		cart.setIdUser(idUser);
		cart.setQuantity(quantitySelected);		
		
		return cartRepository.save(cart);
	}


	/*
	 * Đếm số lượng giỏ hàng (cart size)
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void countCartSize(HttpServletRequest request) {
		// Lấy user id user từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		// Khi chưa đăng nhập: (chưa có token)
		// Load số lượng sản phẩm trên session:
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			int cartSize = 0;
			if(cartsSession != null) {
				cartSize = cartsSession.size();
			}
			session.setAttribute("cartSize", cartSize);
		}
		else {
		// Khi đã đăng nhập
		// Load số lượng sản phẩm trong giỏ hàng ứng với từng idUser:
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			User user = userRepository.getById(idUser);
			String username = user.getUsername();
			session.setAttribute("username", username);
			List<Cart> listProductInCarts = cartRepository.findByIdUser(idUser);
			if( listProductInCarts != null ) {
				int totalQuantity = listProductInCarts.size();
				session.setAttribute("cartSize", totalQuantity);						
			}
			else {
				session.setAttribute("cartSize", 0);
			}
		}
	}


	/*
	 * Update số lượng sản phẩm
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@Override
	public Cart update(Long id, Long quantityUpdate) {
		Cart cart = cartRepository.findById(id).get();
		Long quantityCurrent = cart.getQuantity();
		cart.setQuantity(quantityCurrent + quantityUpdate);
		cartRepository.save(cart);
		return cart;
	}
	
}
