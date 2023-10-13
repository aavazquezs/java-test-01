package com.avangenio.warehouse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailService customUserDetailService;
	
	private final UnauthorizedHandler unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUserDetailService)
				.passwordEncoder(passwordEncoder())
				.and().build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http
				.csrf(csrf -> csrf.disable())

				.cors(cors -> cors.disable())

				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.formLogin(formLogin -> formLogin.disable())
				
				.exceptionHandling(exceptionHandling->exceptionHandling.authenticationEntryPoint(unauthorizedHandler))

				.securityMatcher("/**")

				.authorizeHttpRequests(

						(authorize) -> authorize

								.requestMatchers("/api/api-docs/**").permitAll()

								.requestMatchers("/api/swagger-ui/**").permitAll()

								.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

								.requestMatchers("/api/sections", "/api/products").hasRole("USER")

								.requestMatchers(HttpMethod.DELETE, "/api/section").hasRole("ADMIN")

								// .requestMatchers("/", "/**").permitAll()

								.anyRequest().authenticated());

		return http.build();
	}
}
