package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.iofile.IOFile;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.admin.CSVService;

@Service
public class CSVServiceImpl implements CSVService {
	
	@Autowired
	ProductRepository productRepository;

	/*
	 * Lưu danh sách sản phẩm vào database
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void save(MultipartFile file) {
		try {
			List<Product> products = IOFile.csvProducts(file.getInputStream());
			productRepository.saveAll(products);
		} catch (IOException e) {
//			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
}
