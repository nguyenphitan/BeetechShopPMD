package com.nguyenphitan.BeetechAPI.controller.admin;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.admin.AdminProductService;

/**
 * Quản lí sản phẩm: thêm, sửa, xóa
 * Created by: NPTAN (10/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/api/v1/products")
public class AdminProductController {
	
	@Autowired
	AdminProductService adminProductService;
	
	@Autowired
	ProductRepository productRepository;
	
	/*
	 * Thêm mới sản phẩm
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PostMapping()
	public RedirectView addImage(
			@RequestParam("name") String name, 
			@RequestParam("price") Long price, 
			@RequestParam("quantity") Long quantity,
			@RequestParam("image") MultipartFile multipartFile) throws IOException  
	{
		adminProductService.addNewProduct(name, price, quantity, multipartFile);
        return new RedirectView("/");
	}

	
	/*
	 * Cập nhật thông tin sản phẩm
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product) {
		return productRepository.save(product);
	}

	
	/*
	 * Xóa sản phẩm
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") Long id) {
		adminProductService.deleteProduct(id);
	}
}
