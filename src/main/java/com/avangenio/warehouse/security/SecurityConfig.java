package com.avangenio.warehouse.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.avangenio.warehouse.model.User;
import com.avangenio.warehouse.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

//	@Bean
//	public UserDetailsService userDetailsService(UserRepository userRepo) {
//		return username -> {
//			User user = userRepo.findByUsername(username);
//			if (user != null)
//				return user;
//			throw new UsernameNotFoundException("User '" + username + "' not found");
//		};
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		http
				.csrf(csrf -> csrf.disable())

				.cors(cors -> cors.disable())

				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.formLogin(formLogin -> formLogin.disable())

				.securityMatcher("/**")

				.authorizeHttpRequests(

						(authorize) -> authorize

								.requestMatchers("/api/api-docs").permitAll()

								.requestMatchers("/api/swagger-ui").permitAll()

								.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

								.requestMatchers("/api/sections", "/api/products").hasRole("USER")

								.requestMatchers("/api/section").hasRole("ADMIN")

								// .requestMatchers("/", "/**").permitAll()

								.anyRequest().authenticated());

		return http.build();
	}
}
