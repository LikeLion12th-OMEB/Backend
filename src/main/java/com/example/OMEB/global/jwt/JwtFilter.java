package com.example.OMEB.global.jwt;

import com.example.OMEB.global.base.dto.FailedResponseBody;
import com.example.OMEB.global.base.exception.ErrorCode;
import com.example.OMEB.global.jwt.exception.CustomJwtException;
import com.example.OMEB.global.jwt.exception.JwtNotExistException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static com.example.OMEB.global.utils.CookieUtils.objectMapper;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final String HTTP_AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer ";
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwtToken = resolveToken(request).orElseThrow(() ->
                    new JwtNotExistException(ErrorCode.JWT_NOT_EXIST));
            if (jwtUtils.validateToken(jwtToken)){
                Authentication authentication = jwtUtils.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (CustomJwtException e){
            response.setStatus(e.getErrorCode().getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(
                    FailedResponseBody.createFailureResponse(e.getErrorCode())));
        }
    }

    public Optional<String> resolveToken(HttpServletRequest request){
        // jwt 토큰 파싱
        String bearerToken = request.getHeader(HTTP_AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_TYPE))
            return Optional.of(bearerToken.substring(7));

        return Optional.empty();
    }
}
