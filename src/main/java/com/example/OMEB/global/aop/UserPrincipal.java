package com.example.OMEB.global.aop;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.media.Schema;

@AuthenticationPrincipal
@Schema(hidden = true)
public @interface UserPrincipal {
}
