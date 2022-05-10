package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.entity.OrderDetail;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.payload.BillResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.payload.ProductResponse;
import com.nguyenphitan.BeetechAPI.repository.OrderAccountRepository;
import com.nguyenphitan.BeetechAPI.repository.OrderDetailRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.BillService;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

@Service
public class BillServiceImpl implements BillService {
	
	@Autowired
	private OrderAccountRepository orderAccountRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DiscountService discountService;
	
	/*
	 * Hiển thị danh sách hóa đơn
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public ModelAndView billManagerPage() {
		ModelAndView modelAndView = new ModelAndView("admin/bill");
		
		// 1. Lấy ra danh sách các hóa đơn:
		List<OrderAccount> orderAccounts = orderAccountRepository.findAll();
		
		// 2. Lấy ra danh sách chi tiết của các hóa đơn:
		List<OrderDetail> orderDetails = orderDetailRepository.findAll();
		
		// 3. Danh sách dữ liệu hóa đơn trả về: (id hóa đơn, thông tin khách hàng, danh sách sản phẩm kèm số lượng, tổng tiền, ngày mua, trạng thái hóa đơn)
		List<BillResponse> billResponses = new ArrayList<BillResponse>();
		for(OrderAccount orderAccount : orderAccounts) {
			// Lấy ra id hóa đơn:
			Long billId = orderAccount.getId();
			// Lấy ra thông tin nhân viên:
			User user = userRepository.getById(orderAccount.getUserId());
			// Lấy ra mua: (ngày tạo hóa đơn)
			Date orderDate = orderAccount.getOrderDate();
			// Lấy ra trạng thái hóa đơn:
			Integer status = orderAccount.getStatus();
			// List danh sách sản phẩm trong hóa đơn:
			List<ProductResponse> products = new ArrayList<ProductResponse>();
			// Lấy ra tất cả các mã giảm giá:
			List<Discount> discounts = discountService.getAlls();
			// Tổng tiền hóa đơn:
			Double total = 0D;
			for(OrderDetail orderDetail : orderDetails) {
				if( orderAccount.getId() == orderDetail.getOrderAccountId() ) {
					// Thêm sản phẩm vào list hóa đơn:
					Product product = productRepository.getById(orderDetail.getProductId());
					products.add( new ProductResponse(product, orderDetail.getQuantity()) );
					Long totalPrice = orderDetail.getQuantity() * product.getPrice();
					total += totalPrice;
				}
			}
			
			for(int i = 0 ; i < discounts.size() ; i++) {
				Double nextValue = discounts.get(i).getValue();
				if( total < nextValue ) {
					if( i > 0 ) {
						total -= (total * discounts.get(i-1).getDiscount()/100);
						break;						
					}
					else {
						break;
					}
				}
			}
			
			billResponses.add( new BillResponse(billId, user, products, orderDate, status, total) );
			
		}
		
		modelAndView.addObject("billResponses", billResponses);
		return modelAndView;
	}

	
	/*
	 * Tạo hóa đơn
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public OrderAccount create(BillRequest billRequest) {
		// Lấy ra useId:
		Long userId = billRequest.getUserId();
		
		// 1. Tạo một order_account -> Lưu vào db:
		OrderAccount orderAccount = new OrderAccount(userId);
		
		// 2. Lấy ra order_account_id vừa mới lưu vào db:
		OrderAccount orderAccountCurrentSave = orderAccountRepository.save(orderAccount);
		Long orderAccountId = orderAccountCurrentSave.getId();
		
		// 3. Tạo các order_detail ứng với order_account_id và danh sách sản phẩm -> Lưu vào db:
		List<ProductRequest> productRequests = billRequest.getProductRequests();
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for(ProductRequest productRequest : productRequests) {
			OrderDetail orderDetail = new OrderDetail(orderAccountId, productRequest.getIdProduct(), productRequest.getQuantitySelected());
			orderDetails.add(orderDetail);
		}
		orderDetailRepository.saveAll(orderDetails);
		
		return orderAccountCurrentSave;
	}

	
	/*
	 * Cập nhật hóa đơn
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public OrderAccount update(Long id) {
		OrderAccount orderAccount = orderAccountRepository.getById(id);
		orderAccount.setStatus(1);
		return orderAccountRepository.save(orderAccount);
	}

}
