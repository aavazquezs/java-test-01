package com.avangenio.warehouse.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory= WithMockUserSecurityContextFactory.class)
public @interface WithMockUser {
	String userId() default "ebd712cd-f3de-46d3-99f7-19441ab2a6c9";
	String[] authorities() default "ROLE_USER";
	
	
}
