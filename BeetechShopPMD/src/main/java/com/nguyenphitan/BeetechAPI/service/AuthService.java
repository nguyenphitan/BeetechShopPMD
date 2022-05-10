package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nguyenphitan.BeetechAPI.entity.User;

public interface AuthService {
	// Kiểm tra đăng nhập:
	String handleLogin(String username, String password, HttpServletRequest request);
	
	// Xử lý đăng ký:
	User handleRegister(String username, String password);
	
	// Xử lý đăng xuất:
	void handleLogout(HttpServletRequest request, HttpServletResponse response);
	
}
