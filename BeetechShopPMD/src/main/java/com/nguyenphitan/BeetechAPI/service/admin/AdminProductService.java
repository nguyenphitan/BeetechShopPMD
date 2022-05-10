package com.nguyenphitan.BeetechAPI.service.admin;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface AdminProductService {
	// Hiển thị danh sách sản phẩm:
	ModelAndView productPage();
	
	// Thêm một sản phẩm mới:
	void addNewProduct(String name, Long price, Long quantity, MultipartFile multipartFile) throws IOException;
	
	// Xóa sản phẩm khỏi gian hàng:
	void deleteProduct(Long id);
}
