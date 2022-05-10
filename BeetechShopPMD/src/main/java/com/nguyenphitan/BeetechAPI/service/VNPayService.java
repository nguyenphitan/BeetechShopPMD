package com.nguyenphitan.BeetechAPI.service;

import org.springframework.web.servlet.ModelAndView;

public interface VNPayService {
	
	// Load dữ liệu cho trang kết quả thanh toán online:
	ModelAndView vnpayReturnPage(String amount, String bankCode, String bankTranNo, String cardType, String orderInfo,
			String payDate, String responseCode, String tmnCode, String transactionNo, String transactionStatus,
			String txnRef, String secureHash);
}
