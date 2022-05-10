package com.nguyenphitan.BeetechAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nguyenphitan.BeetechAPI.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Tạo Bean PasswordEndcoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Tạo Bean AuthenticationManager
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// Get AuthenticationManager Bean
		return super.authenticationManagerBean();
	}

	// Tạo Bean JwtAuthenticationFilter
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers("/api/v1/auth/**").permitAll();
		http.authorizeRequests().antMatchers("/", "/swagger-ui.html", "/resources/**", "/vendor/**", "/css/**",
				"/js/**", "/fonts/**", "/img/**", "/static/**").permitAll();
		http.authorizeRequests().antMatchers("/public/**", "/list-cart", "/cart", "/clone/**", "/list-products/**",
				"/detail/**", "/auth/**", "/pay/**").permitAll();
		http.authorizeRequests().antMatchers("/admin-bill/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SELLER");
		http.authorizeRequests()
				.antMatchers("/admin/**", "/api/v1/csv/**", "/add-list/**", "/public/bill/update/**", "/admin-**/**")
				.hasAnyAuthority("ROLE_ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
	}
}
