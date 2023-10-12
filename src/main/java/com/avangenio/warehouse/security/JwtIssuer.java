package com.avangenio.warehouse.security;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtIssuer {

	private final JwtProperties properties;
	
	public String issue(UUID userId, String username, List<String> roles) {
		return JWT.create()
				.withSubject(String.valueOf(userId))
				.withExpiresAt(Instant.now().plus(Duration.ofDays(1)))
				.withClaim("username", username)
				.withClaim("roles", roles)
				.sign(Algorithm.HMAC256(properties.getSecretKey()));
	}
}
