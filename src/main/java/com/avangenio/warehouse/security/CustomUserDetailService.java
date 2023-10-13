package com.avangenio.warehouse.security;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.avangenio.warehouse.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

	private final UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var user = userService.findByUsername(username).orElseThrow();
		
		return UserPrincipal.builder()
				.userId(user.getId())
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(Arrays.stream(user.getRoles())
						.map(rol->{
							return new SimpleGrantedAuthority(rol);
						})
						.collect(Collectors.toList()))
				.build();
	}

}
