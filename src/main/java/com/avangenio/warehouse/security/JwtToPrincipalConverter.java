package com.avangenio.warehouse.security;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtToPrincipalConverter {
	
	public UserPrincipal convert(DecodedJWT jwt) {
		return UserPrincipal.builder()
				.userId(UUID.fromString(jwt.getSubject()))
				.username(jwt.getClaim("username").asString())
				.authorities(extractAuthoritiesFromClaim(jwt))
				.build();
	}
	
	private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
		var claim = jwt.getClaim("roles");
		if(claim.isNull() || claim.isMissing()) return List.of();
		return claim.asList(SimpleGrantedAuthority.class);
	} 
}
