package com.avangenio.warehouse.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avangenio.warehouse.model.User;
import com.avangenio.warehouse.repository.UserRepository;
import com.avangenio.warehouse.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Optional<User> findByUsername(String username) {
		
		User user = userRepository.findByUsername(username);
		
		if(Objects.nonNull(user)) {
		
			return Optional.of(user);
			
		} 
		
		return Optional.empty();
	}

}
