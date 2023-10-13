package com.avangenio.warehouse.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.avangenio.warehouse.model.LoginResponse;
import com.avangenio.warehouse.security.JwtIssuer;
import com.avangenio.warehouse.security.UserPrincipal;
import com.avangenio.warehouse.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	private final JwtIssuer jwtIssuer;

	@Autowired
	private final AuthenticationManager authenticationManager;

	@Override
	public LoginResponse attemptLogin(String username, String password) {
		var authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		var principal = (UserPrincipal) authentication.getPrincipal();

		var roles = principal.getAuthorities().stream().map(t -> t.getAuthority()).collect(Collectors.toList());

		var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);

		return LoginResponse.builder().accessToken(token).build();
	}

}
