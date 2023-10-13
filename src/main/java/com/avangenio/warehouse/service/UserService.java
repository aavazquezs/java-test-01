package com.avangenio.warehouse.service;

import java.util.Optional;

import com.avangenio.warehouse.model.User;

public interface UserService {
	
	Optional<User> findByUsername(String username);
	
}
