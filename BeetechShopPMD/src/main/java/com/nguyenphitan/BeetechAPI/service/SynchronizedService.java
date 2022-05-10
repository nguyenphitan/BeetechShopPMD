package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.RedirectView;

public interface SynchronizedService {
	RedirectView synchronizedCart(HttpServletRequest request);
}
