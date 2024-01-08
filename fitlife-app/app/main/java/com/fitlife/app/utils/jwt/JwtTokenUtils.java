package com.fitlife.app.Utils.Jwt;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${application.security.jwt.secret-key}")
	private String JWT_SECRET;
	@Value("${application.security.jwt.expiration}")
	private long JWT_EXPIRATION;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long JWT_REFRESH_EXPIRATION;

	public String generateTokenRefresh(String user) {
		Date now = new Date();
		Date expDate = new Date(now.getTime() + JWT_REFRESH_EXPIRATION);

		SecureRandom random = new SecureRandom();
		byte[] nonceBytes = new byte[16];
		random.nextBytes(nonceBytes);
		String nonce = Base64.getEncoder().encodeToString(nonceBytes);

		return Jwts.builder()
				.setSubject(user)
				.setIssuedAt(now)
				.setExpiration(expDate)
				.claim("nonce", nonce)
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET)
				.compact();
	}

	public String generateToken(String subject) {
		long currentTimeMillis = System.currentTimeMillis();
		long expirationTimeMillis = currentTimeMillis + JWT_EXPIRATION; // 1 hour -> 4 hour
		Date issueDate = new Date(currentTimeMillis);
		Date expirationDate = new Date(expirationTimeMillis);

		return Jwts.builder()
				.setSubject(subject).setIssuedAt(issueDate).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
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
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
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
	}

}
