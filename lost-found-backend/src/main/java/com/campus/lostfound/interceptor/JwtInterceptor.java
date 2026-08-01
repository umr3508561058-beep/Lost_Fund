package com.campus.lostfound.interceptor;

import com.campus.lostfound.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null ||
                !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "请先登录"
            );
        }

        String token = authorization.substring(7);

        try {
            Claims claims = jwtUtil.parseToken(token);

            Number userId = claims.get("userId", Number.class);

            request.setAttribute("userId", userId.longValue());
            request.setAttribute("studentNo", claims.getSubject());
            request.setAttribute("username", claims.get("username"));
            request.setAttribute("role", claims.get("role"));

            return true;
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Token无效或已经过期"
            );
        }
    }
}