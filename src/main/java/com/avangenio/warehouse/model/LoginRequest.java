package com.avangenio.warehouse.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {
	
	private String username;
	
	private String password;
	
}
