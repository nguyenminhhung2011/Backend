package com.FitnessApp.Utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenUtils {

	private final String JWT_SECRET = "FitnessHCMUS";
	private String appName = "Fitness";

	private final long JWT_EXPIRATION = 10 * 24 * 60 * 60 * 1000L; // 10 days

	public String generateTokenRefresh(String user) {
		Date now = new Date();
		Date expDate = new Date(now.getTime() + JWT_EXPIRATION);

		// Tạo một phần ngẫu nhiên (nonce)
		SecureRandom random = new SecureRandom();
		byte[] nonceBytes = new byte[16];
		random.nextBytes(nonceBytes);
		String nonce = Base64.getEncoder().encodeToString(nonceBytes);

		return Jwts.builder().setSubject(user).setIssuedAt(now).setExpiration(expDate).claim("nonce", nonce) // Thêm
																												// nonce
																												// vào
																												// claim
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
	}

	public String generateToken(String username, Long userId) {
		long currentTimeMillis = System.currentTimeMillis();
		long expirationTimeMillis = currentTimeMillis + 3600 * 1000 * 4; // 1 hour -> 4 hours
		Date issueDate = new Date(currentTimeMillis);
		Date expirationDate = new Date(expirationTimeMillis);

		return Jwts.builder().claim("username", username).claim("id", userId).setIssuedAt(issueDate)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.get("id").toString());
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String getUsernameFromToken(String token) {

		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
		return (String) claims.get("username");
	}

	public boolean validateToken(String authToken) throws Exception {
		try {
			Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException ex) {

			throw new Exception("Invalid JWT token");
		} catch (ExpiredJwtException ex) {

			throw new Exception("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new Exception("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {

			throw new Exception("JWT claims string is empty");
		}
//		return false;
	}

}
