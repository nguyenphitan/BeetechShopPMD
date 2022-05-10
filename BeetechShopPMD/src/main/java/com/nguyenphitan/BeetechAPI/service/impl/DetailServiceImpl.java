package com.nguyenphitan.BeetechAPI.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;
import com.nguyenphitan.BeetechAPI.service.DetailService;

@Service
public class DetailServiceImpl implements DetailService {

	@Autowired
	private CartService cartService;
	
	@Autowired 
	private ProductRepository productRepository;
	
	@Override
	public ModelAndView detailPage(Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("product", productRepository.findById(id));
		cartService.countCartSize(request);
		return modelAndView;
	}

}
