package com.nguyenphitan.BeetechAPI.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;

/**
 * Quản lý tài khoản
 * Created by: NPTAN (08/05/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/user")
public class AdminAccountController {
	
	@Autowired
	UserRepository userRepository;

	/*
	 * Hiển thị tất cả tài khoản
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0
	 */
	@GetMapping
	public ModelAndView accountPage() {
		ModelAndView modelAndView = new ModelAndView("admin/user");
		List<User> users = userRepository.findAll();
        modelAndView.addObject("users", users);
		return modelAndView;
	}
	
}
