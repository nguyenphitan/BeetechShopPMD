package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface DetailService {
	// Hiển thị chi tiết sản phẩm:
	ModelAndView detailPage(Long id, HttpServletRequest request);
}
