package com.avangenio.warehouse.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avangenio.warehouse.model.LoginRequest;
import com.avangenio.warehouse.model.LoginResponse;
import com.avangenio.warehouse.security.JwtIssuer;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private final JwtIssuer jwtIssuer;

	@PostMapping("/api/auth/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		
		var token = jwtIssuer.issue(UUID.fromString("ebd712cd-f3de-46d3-99f7-19441ab2a6c9"), request.getUsername(), List.of("ROLE_USER"));
		
		return LoginResponse
				.builder()
				.accessToken(token)
				.build();
	}
}
