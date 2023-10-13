package com.avangenio.warehouse.controller;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.avangenio.warehouse.security.UserPrincipal;
import com.avangenio.warehouse.security.UserPrincipalAuthenticationToken;

public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser>{

	@Override
	public SecurityContext createSecurityContext(WithMockUser annotation) {
		var authorities = Arrays.stream(annotation.authorities())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		var principal = UserPrincipal.builder()
				.userId(UUID.fromString(annotation.userId()))
				.username("fakeUser")
				.authorities(authorities)
				.build();
		var context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(new UserPrincipalAuthenticationToken(principal));
		return context;
	}

}
