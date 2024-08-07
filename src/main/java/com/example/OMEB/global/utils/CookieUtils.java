package com.example.OMEB.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtils {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void createCookie(HttpServletResponse httpServletResponse,
                                      String name, String value, int maxAge){
        String cookieString = ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build()
                .toString();
        httpServletResponse.addHeader("Set-Cookie", cookieString);
    }
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null){
            for(Cookie cookie : cookies){
                if (cookie.getName().equals(name)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void deleteCookie(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    String name){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    httpServletResponse.addCookie(cookie);
                }
            }
        }
    }

    public static String ObjectToString(Object object)  {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(object));
    }

    public static <T> T StringToObject(String string, Class<T> classType) {
        return classType.cast(SerializationUtils.deserialize
                (Base64.getUrlDecoder().decode(string)));
    }
}
