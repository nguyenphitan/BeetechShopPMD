package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.admin.SellerService;

@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> getAllSellers() {
		List<User> accounts = userRepository.findByRole("SELLER");
		return accounts;
	}

	@Override
	public int addSeller(int id) {
		Integer result = userRepository.addSeller("SELLER", id);
		return result;
	}

	@Override
	public void deleteSeller(int id) {
		userRepository.addSeller("USER", id);
	}

	
}
