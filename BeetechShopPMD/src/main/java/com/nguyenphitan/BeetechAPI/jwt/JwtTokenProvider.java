package com.nguyenphitan.BeetechAPI.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.nguyenphitan.BeetechAPI.custom.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	// Đoạn mã JWT_SECRET
	private static final String JWT_SECRET = "nguyenphitan";

	// Thời gian hiệu lực của mỗi chuỗi jwt
	private static final long JWT_EXPIRATION = 604800000L;

	// Tạo ra jwt từ thông tin user
	public String generateToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		// Tạo chuỗi jwt từ id của user
		return Jwts.builder()
					.setSubject(Long.toString(userDetails.getUser().getId()))
					.setIssuedAt(now)
					.setExpiration(expiryDate)
					.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
					.compact();
	}
	
	// Lấy thông tin user từ jwt
	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
							.setSigningKey(JWT_SECRET)
							.parseClaimsJws(token)
							.getBody();
		
		return Long.parseLong(claims.getSubject());
	}
	
	// Validate token
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty.");
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token.");
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token.");
		} catch (UnsupportedJwtException e) {
			log.error("Unsupported JWT token.");
		}
		return false;
	}

}
