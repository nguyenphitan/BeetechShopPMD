package com.nguyenphitan.BeetechAPI.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.service.admin.SellerService;

/**
 * Quản lý nhân viên
 * Created by: NPTAN (08/05/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/seller")
public class AdminSellerController {
	
	@Autowired
	SellerService sellerService;
	
	/*
	 * Hiển thị ra danh sách nhân viên
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0
	 */
	@GetMapping
	public ModelAndView sellerPage() {
		ModelAndView modelAndView = new ModelAndView("admin/seller");
		modelAndView.addObject("sellers", sellerService.getAllSellers());
		return modelAndView;
	}
	
	/*
	 * Thêm nhân viên (add role SELLER cho tài khoản)
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0
	 */
	@PostMapping
	public Integer addSeller(@RequestBody int id) {
		return sellerService.addSeller(id);
	}
	
	/*
	 * Xóa nhân viên (chỉ xóa role, không xóa tài khoản)
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0 
	 */
	@DeleteMapping("/{id}")
	public void deleteSeller(@PathVariable("id") int id) {
		sellerService.deleteSeller(id);
	}
	
}
