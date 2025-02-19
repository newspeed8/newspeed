package com.example.newspeed.auth.controller;

import com.example.newspeed.auth.service.JwtBlackListService;
import com.example.newspeed.auth.util.JwtUtil;
import com.example.newspeed.auth.dto.UserLoginRequestDto;
import com.example.newspeed.exception.ApplicationException;
import com.example.newspeed.exception.InvalidCredentialException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtBlackListService jwtBlackListService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody UserLoginRequestDto dto
    ) {
        if("user".equals(dto.getEmail()) && "password".equals(dto.getPassword())) {
            String token = JwtUtil.generateToken(dto.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            throw new InvalidCredentialException("로그인에 실패하였습니다");
        }
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ApplicationException("유효하지 않은 요청입니다", HttpStatus.UNAUTHORIZED);
        }
        //공백 포함(Bearer) 접두사 제거
        String token = authHeader.substring(7);
        long expirationTime = Long.parseLong(JwtUtil.getExpirationTime(token)); // 토큰의 남은 만료 시간 계산
        jwtBlackListService.blackListToken(token, expirationTime);
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃 성공");
        return response;
    }


}
