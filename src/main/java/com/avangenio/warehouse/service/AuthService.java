package com.avangenio.warehouse.service;

import com.avangenio.warehouse.model.LoginResponse;

public interface AuthService {
	
	public LoginResponse attemptLogin(String username, String password);
	
}
