package com.example.newspeed.common.filter;

import com.example.newspeed.auth.service.JwtBlackListService;
import com.example.newspeed.auth.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/users/signup", "/login", "/logout", "/users/findall", "/users/find/*"};
    private final JwtBlackListService blackListService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (isWhiteList(httpRequest.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않거나 존재하지 않는 헤더입니다");
            return;
        }
        //공백 포함(Bearer) 접두사 제거
        String token = authHeader.substring(7);
        if (blackListService.isTokenBlacklisted(token)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그아웃된 토큰입니다");
            return;
        }
        String username = JwtUtil.validateToken(token);
        if (username == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

