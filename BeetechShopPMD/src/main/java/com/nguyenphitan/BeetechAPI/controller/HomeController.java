package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.BillService;
import com.nguyenphitan.BeetechAPI.service.CartService;
import com.nguyenphitan.BeetechAPI.service.DetailService;
import com.nguyenphitan.BeetechAPI.service.VNPayService;
import com.nguyenphitan.BeetechAPI.service.admin.AdminProductService;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

/**
 * Controller quản lý các page
 * Created by: NPTAN
 * Version: 1.0
 */
@Controller
public class HomeController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private DiscountService discountService;
	
	@Autowired
	private VNPayService vnPayService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private AdminProductService adminProductService;
	
	@Autowired
	private DetailService detailService;
	
	
	/*
	 * Hiển thị trang home (danh sách các sản phẩm)
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/")
	public ModelAndView indexPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("products", productRepository.findAll());
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị đăng nhập
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/login")
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}
	
	
	/*
	 * Hiển thị đăng ký
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/register")
	public ModelAndView registerPage() {
		return new ModelAndView("register");
	}
	
	
	/*
	 * Hiển thị trang quản lý sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin-product")
	public ModelAndView productPage() {
		return adminProductService.productPage();
	}
	
	
	/*
	 * Hiển thị trang thêm mới sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-product")
	public ModelAndView addProductPage() {
		return new ModelAndView("admin/add-product");
	}
	
	
	/*
	 * Hiển thị trang thêm mới danh sách sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-list")
	public ModelAndView addListProduct() {
		return new ModelAndView("admin/add-list-product");
	}
	
	
	/*
	 * Hiển thị trang chi tiết sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/detail")
	public ModelAndView detailPage(@RequestParam("id") Long id, HttpServletRequest request) {
		return detailService.detailPage(id, request);
	}
	
	
	/*
	 * Hiển thị danh sách sản phẩm trong giỏ hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/list-cart")
	public ModelAndView cartPage(HttpServletRequest request) {
		return cartService.getAllCart("cart", request);
	}
	
	
	/*
	 * Hiển thị trang thêm mới mã giảm giá
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-discount")
	public ModelAndView createDiscount() {
		return new ModelAndView("admin/add-discount");
	}
	
	
	/*
	 * Hiển thị trang quản lý hóa đơn
	 * Created by: NPTAN (06/05/2022)
	 * Version: 1.0
	 */
	@GetMapping("/admin-bill")
	public ModelAndView billManagerPage() {
		return billService.billManagerPage();
	}
	
	
	/*
	 * Hiển thị giao diện thanh toán online
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/payment")
	public ModelAndView payment() {
		return new ModelAndView("vnpay");
	}
	
	
	/*
	 * Hiển thị trang quản lý mã giảm giá
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin-discount")
	public ModelAndView discountPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/discount");
		modelAndView.addObject("discounts", discountService.getAlls());
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị thông tin sau khi thanh toán cho khách hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/vnpay_return")
	public ModelAndView returnPage(
			@RequestParam("vnp_Amount") String amount,
			@RequestParam("vnp_BankCode") String bankCode,
			@RequestParam("vnp_BankTranNo") String bankTranNo,
			@RequestParam("vnp_CardType") String cardType,
			@RequestParam("vnp_OrderInfo") String orderInfo,
			@RequestParam("vnp_PayDate") String payDate,
			@RequestParam("vnp_ResponseCode") String responseCode,
			@RequestParam("vnp_TmnCode") String tmnCode,
			@RequestParam("vnp_TransactionNo") String transactionNo,
			@RequestParam("vnp_TransactionStatus") String transactionStatus,
			@RequestParam("vnp_TxnRef") String txnRef,
			@RequestParam("vnp_SecureHash") String secureHash
			
	) {
		return vnPayService.vnpayReturnPage(
				amount, bankCode, bankTranNo, cardType, orderInfo, payDate, responseCode, tmnCode, transactionNo, transactionStatus, txnRef, secureHash);
	}
}
