package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

public interface PayService {
	// Thanh toán đơn hàng:
	void payment(HttpServletRequest request);
}
