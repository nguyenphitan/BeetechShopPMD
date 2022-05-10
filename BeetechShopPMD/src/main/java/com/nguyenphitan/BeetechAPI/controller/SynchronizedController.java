package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.service.SynchronizedService;

/**
 * Đồng bộ giỏ hàng
 * Created by: NPTAN
 * Version: 1.0
 */
@RestController
@RequestMapping("/synchronized")
public class SynchronizedController {
	
	@Autowired
	SynchronizedService synchronizedService;
	
	/*
	 * Đồng bộ giỏ hàng khi đăng nhập thành công
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/cart")
	public RedirectView synchronizedCart(HttpServletRequest request) {
		return synchronizedService.synchronizedCart(request);
	}
	
}
