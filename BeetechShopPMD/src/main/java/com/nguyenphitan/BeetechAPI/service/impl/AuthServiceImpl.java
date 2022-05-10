package com.nguyenphitan.BeetechAPI.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.custom.CustomUserDetails;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.LoginRequest;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * Kiểm tra thông tin đăng nhập
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public String handleLogin(String username, String password, HttpServletRequest request) {
		String jwt = null;
		try {
			// Tạo ra LoginRequest từ username và password nhận được từ client
			LoginRequest loginRequest = new LoginRequest(username, password);
			// Xác thực thông tin người dùng Request lên:
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), 
							loginRequest.getPassword()
					)
			);
			// Nếu không xảy ra exception tức là thông tin hợp lệ
			// Set thông tin authentication vào Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// Trả về jwt cho người dùng
			jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
			// Lưu jwt vào session:
			HttpSession session = request.getSession();
			session.setAttribute("token", jwt);
			
			Long userId = tokenProvider.getUserIdFromJWT(jwt);
			User user = userRepository.getById(userId);
			session.setAttribute("role", user.getRole());
		} catch (Exception e) {
//			System.out.println(e);
		}
		
		return jwt;
	}

	
	/*
	 * Xử lý thông tin đăng ký tài khoản
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public User handleRegister(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");
		userRepository.save(user);
		return user;
	}

	
	/*
	 * Xử lý đăng xuất
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// Xóa token khỏi session:
		session.removeAttribute("token");
		// Xóa fullname khỏi session:
		session.removeAttribute("username");
		// Xóa role:
		session.removeAttribute("role");
		// Xóa tất cả cookies:
		for (Cookie cookie : request.getCookies()) {
		    cookie.setValue("");
		    cookie.setMaxAge(0);
		    cookie.setPath("/");

		    response.addCookie(cookie);
		}
	}

}
