package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * Lấy ra danh sách các user
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	
	/*
	 * Lấy ra user theo id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("id") Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User id invalid.")
		);
	}

	
	/*
	 * Cập nhật thông tin user
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	
	/*
	 * Xóa user
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userRepository.deleteById(id);
	}
}