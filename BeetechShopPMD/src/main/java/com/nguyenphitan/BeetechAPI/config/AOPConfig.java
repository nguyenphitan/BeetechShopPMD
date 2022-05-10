package com.nguyenphitan.BeetechAPI.config;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AOPConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AOPConfig.class);
	
	@Autowired
	HttpSession session;
	
	@Before("execution(* com.nguyenphitan.BeetechAPI.controller.*.*(..))")
	public void handleUserLogin(JoinPoint joinPoint) throws Exception {
		String token = session.getAttribute("token") == null ? null : (String) session.getAttribute("token");
		LOGGER.info(token);
	}
	
}
