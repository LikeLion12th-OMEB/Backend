package com.example.OMEB.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

@Aspect
@Component
public class UserIdInjectionAspect {
    @Pointcut("@annotation(com.example.OMEB.global.aop.AssignUserId) && (args(userId, ..) || args(.., userId))")
    public void userIdAnnotatedMethod(Long userId) {}

    @Around(value = "userIdAnnotatedMethod(userId)", argNames = "joinPoint,userId")
    public Object injectUserId(ProceedingJoinPoint joinPoint, Long userId) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // TODO : Authentication 커스텀 객체로 변경
        if(authentication instanceof Authentication) {
            userId = (Long) authentication.getPrincipal();
        } else {
            throw new IllegalArgumentException("Only JWT-based authentication is supported for @AssignUserId annotation.");
        }

        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType() == Long.class && args[i] == null) {
                args[i] = userId;
                break;
            }
        }

        return joinPoint.proceed(args);
    }
}
