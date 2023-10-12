package com.avangenio.warehouse.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.avangenio.warehouse.model.User;

public interface UserRepository extends CrudRepository<User, UUID>{
	User findByUsername(String username);
}
