package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.service.BillService;

@RestController
@RequestMapping("/public/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	
	/*
	 * Tạo hóa đơn
	 * 1. Lưu id hóa đơn, id khách hàng vào bảng order_account, ngày đặt hàng
	 * 2. Lưu id hóa 
	 */
	@PostMapping
	public OrderAccount create(@RequestBody BillRequest billRequest) {
		return billService.create(billRequest);
	}
	
	
	/*
	 * Cập nhật hóa đơn (chỉ cập nhật status)
	 * Created by: NPTAN (06/05/2022)
	 * Version: 1.0
	 */
	@PutMapping("update/{id}")
	public OrderAccount update(@PathVariable("id") Long id) {
		return billService.update(id);
	}
	
}
