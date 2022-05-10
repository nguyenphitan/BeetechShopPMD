package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.admin.AdminProductService;

@Service
public class AdminProductServiceimpl implements AdminProductService {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	/*
	 * Hiển thị danh sách tất cả các sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public ModelAndView productPage() {
		ModelAndView modelAndView = new ModelAndView("admin/product");
		List<Product> products = productRepository.findAll();
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	
	
	/*
	 * Thêm mới sản phẩm vào gian hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void addNewProduct(String name, Long price, Long quantity, MultipartFile multipartFile) throws IOException {
		Path staticPath = Paths.get("src/main/resources/static");
        Path imagePath = Paths.get("img");
        // Kiểm tra tồn tại hoặc tạo thư mục /static/images
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhotos(imagePath.resolve(multipartFile.getOriginalFilename()).toString());
        productRepository.save(product);
		
	}

	
	/*
	 * Xóa sản phẩm khỏi gian hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void deleteProduct(Long id) {
		// Xóa sản phẩm trong danh sách product:
		productRepository.deleteById(id);
		
		// Xóa sản phẩm trong giỏ hàng:
		cartRepository.deleteByIdProduct(id);
	}


}
