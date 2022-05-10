package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.iofile.IOFile;
import com.nguyenphitan.BeetechAPI.service.admin.CSVService;

/**
 * Admin thêm mới nhiều sản phẩm. (csv file)
 * Created by: NPTAN
 * Version: 1.0
 */
@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/v1/csv")
public class CSVController {
	@Autowired
	CSVService fileService;

	/*
	 * Upload file csv chứa danh sách các sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@PostMapping("/upload")
	public RedirectView uploadFile(Model model, @RequestParam("file") MultipartFile file) {
		String message = "";
		if (IOFile.hasCSVFormat(file)) {
			try {
				fileService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return new RedirectView("/admin-product");
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				model.addAttribute("message", message);
				return new RedirectView("/add-list");
			}
		}
		message = "Please upload a csv file!";
		return new RedirectView("/admin-product");
	}

}