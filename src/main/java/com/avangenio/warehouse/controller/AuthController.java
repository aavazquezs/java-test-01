package com.avangenio.warehouse.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avangenio.warehouse.model.LoginRequest;
import com.avangenio.warehouse.model.LoginResponse;
import com.avangenio.warehouse.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/api/auth/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		
		return authService.attemptLogin(request.getUsername(), request.getPassword());
		
	}
}
