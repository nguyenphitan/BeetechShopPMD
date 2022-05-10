package com.nguyenphitan.BeetechAPI.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

/*
 * Admin thêm, sửa, xóa mã giảm giá
 * Created by: NPTAN (15/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/api/v1/discounts")
public class AdminDiscountController {
	
	@Autowired
	DiscountService discountService;
	
	/*
	 * Lấy ra tất cả mã giảm giá
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@GetMapping()
	public List<Discount> getAlls() {
		return discountService.getAlls();
	}
	
	
	/*
	 * Lấy ra mã giảm giá theo id
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@GetMapping("/{id}")
	public Discount getById(@PathVariable("id") Long id) {
		return discountService.getById(id);
	}
	
	
	/*
	 * Thêm mới mã giảm giá
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@PostMapping()
	public RedirectView addDiscount(@RequestParam("discount") Double discount, @RequestParam("value") Double value) {
		return discountService.createDiscount(discount, value);
	}
	
	
	/*
	 * Cập nhật mã giảm giá
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@PutMapping("/{id}")
	public Discount updateDiscount(@PathVariable("id") Long id, @RequestBody Discount discount) {
		return discountService.update(id, discount);
	}
	
	
	/*
	 * Loại bỏ mã giảm giá
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@DeleteMapping("/{id}") 
	public void deleteDiscount(@PathVariable("id") Long id) {
		discountService.delete(id);
	}
	
}
