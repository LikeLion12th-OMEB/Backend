package com.example.OMEB.global.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.media.Schema;

@AuthenticationPrincipal
@Schema(hidden = true)
@Target({ ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPrincipal {
}
